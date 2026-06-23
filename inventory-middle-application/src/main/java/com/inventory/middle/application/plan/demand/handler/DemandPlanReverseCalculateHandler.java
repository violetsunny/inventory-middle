package com.inventory.middle.application.plan.demand.handler;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.DemandPlanMaterialDetailTypeEnum;
import com.inventory.middle.domain.plan.common.enums.DemandStrategyEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.demand.convertor.ReverseCalculateRequestConverter;
import com.inventory.middle.application.plan.plan.calculate.support.formula.GrossRequirementFormula;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.DemandFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.ParameterFactor;
import com.inventory.middle.infra.plan.persistence.dao.DemandPlanMaterialDetailDao;
import com.inventory.middle.infra.plan.persistence.dao.plan.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.plan.DemandPlanMaterialDetailReqCondition;
import com.inventory.middle.infra.plan.persistence.condition.plan.PlanMaterialParamQueryCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.application.plan.support.ProductSupportService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.BatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 计算冲销结果
 * @author:Vincent.Xiao
 * @date:2021/10/11 10:28
 */
@Component
@Slf4j
public class DemandPlanReverseCalculateHandler {

    @Autowired
    private DemandPlanMaterialDetailDao demandPlanMaterialDetailDao;

    @Autowired
    private PlanConfigDao planConfigDao;

    @Resource
    private GrossRequirementFormula grossRequirementFormula;

    @Autowired
    private ProductSupportService productSupportService;

    @Autowired
    private InventorySupportService inventorySupportService;


    public void calculate(ReverseCalculateRequestContext request) {

        log.info("DemandPlanReverseCalculateHandler request:{}", JSON.toJSONString(request));
        List<DemandPlanMaterialDetailReqCondition> reqList = ReverseCalculateRequestConverter.INSTANCE.toConditionPOList(request.getMaterialList());

        //查询物料的预测需求
        reqList.forEach(reqCondition -> {
            reqCondition.setType(DemandPlanMaterialDetailTypeEnum.PLAN.getCode());
        });
        List<DemandPlanMaterialDetailPO> planList = demandPlanMaterialDetailDao.findDetailsByCondition(reqList, true);
        Map<String, List<DemandPlanMaterialDetailPO>> planMap = planList.stream().collect(Collectors.groupingBy(k -> k.getMaterialCode() + CommonConstants.DELIMITER_COMMA + k.getLogicalPlantNo() + CommonConstants.DELIMITER_COMMA + k.getTenantId()));


        //查询物料的订单需求
        reqList.forEach(reqCondition -> {
            reqCondition.setType(DemandPlanMaterialDetailTypeEnum.ORDER.getCode());
        });
        List<DemandPlanMaterialDetailPO> orderList = demandPlanMaterialDetailDao.findDetailsByCondition(reqList, true);
        Map<String, List<DemandPlanMaterialDetailPO>> orderMap = orderList.stream().collect(Collectors.groupingBy(k -> k.getMaterialCode() + CommonConstants.DELIMITER_COMMA + k.getLogicalPlantNo() + CommonConstants.DELIMITER_COMMA + k.getTenantId()));

        //查询物料的冲销
        reqList.forEach(reqCondition -> {
            reqCondition.setType(DemandPlanMaterialDetailTypeEnum.REVERSE.getCode());
        });
        List<DemandPlanMaterialDetailPO> reverseList = demandPlanMaterialDetailDao.findDetailsByCondition(reqList, true);


        //查询物料计划参数配置

        //Map<String, PlanMaterialParameterPO> parameterPOMap = queryPlanMaterialParam(reqList);

        //物料，逻辑仓，租户id 维度分组
        Map<String, DemandPlanMaterialDetailReqCondition> materialMap = reqList.stream().collect
                (Collectors.toMap(k -> k.getMaterialCode() + CommonConstants.DELIMITER_COMMA + k.getLogicalPlantNo() + CommonConstants.DELIMITER_COMMA + k.getTenantId(),
                        v -> v, (v1, v2) -> v2));

        List<DemandPlanMaterialDetailPO> updateList = new ArrayList<>();
        List<DemandPlanMaterialDetailPO> insertList = new ArrayList<>();
        for (String key : materialMap.keySet()) {
            DemandPlanMaterialDetailReqCondition reqCondition = materialMap.get(key);
            List<DemandPlanMaterialDetailPO> planDetailList = planMap.get(key);
            List<DemandPlanMaterialDetailPO> orderDetailList = orderMap.get(key);
            /*PlanMaterialParameterPO parameterPO = parameterPOMap.get(key);
            if (Objects.isNull(parameterPO)) {
                continue;
            }*/
            //11.01需求变更，都是根据mts12 计算
            PlanMaterialParameterPO parameterPO = new PlanMaterialParameterPO();
            parameterPO.setDemandStrategyCode(DemandStrategyEnum.MTS11.getCode());
            parameterPO.setLogicalPlantNo(key.split(CommonConstants.DELIMITER_COMMA)[1]);
            parameterPO.setTenantId(key.split(CommonConstants.DELIMITER_COMMA)[2]);
            parameterPO.setMaterialCode(key.split(CommonConstants.DELIMITER_COMMA)[0]);
            //计算冲销值
            Map<Date, Long> resultMap = calculateReverseAmount(parameterPO, planDetailList, orderDetailList);
            for (Date resultKey : resultMap.keySet()) {
                DemandPlanMaterialDetailPO reverseDetail = reverseList.stream().filter(
                        k -> {
                            return Objects.equals(k.getPlanDate(), resultKey)
                                    && Objects.equals(k.getLogicalPlantNo(), reqCondition.getLogicalPlantNo())
                                    && Objects.equals(k.getMaterialCode(), reqCondition.getMaterialCode())
                                    && Objects.equals(k.getTenantId(), reqCondition.getTenantId());
                        }
                ).findFirst().orElse(null);
                if (Objects.nonNull(reverseDetail)) {
                    reverseDetail.setAmount(resultMap.get(resultKey));
                    updateList.add(reverseDetail);
                } else {
                    InvPlantBO logicalPlant = inventorySupportService.findPlant(reqCondition.getLogicalPlantNo(), reqCondition.getTenantId());
                    ProductBO product = productSupportService.findProduct(reqCondition.getMaterialCode(), reqCondition.getTenantId());
                    DemandPlanMaterialDetailPO detailPO = new DemandPlanMaterialDetailPO();
                    detailPO.setMaterialCode(reqCondition.getMaterialCode());
                    detailPO.setMaterialDesc(Objects.isNull(product) ? null : product.getName());
                    detailPO.setMaterialUnit(Objects.isNull(product) ? null : product.getUnit());
                    detailPO.setLogicalPlantNo(reqCondition.getLogicalPlantNo());
                    detailPO.setLogicalPlantName(Objects.isNull(logicalPlant) ? null : logicalPlant.getPlantName());
                    detailPO.setPlanDate(resultKey);
                    detailPO.setAmount(resultMap.get(resultKey));
                    detailPO.setType(DemandPlanMaterialDetailTypeEnum.REVERSE.getCode());
                    detailPO.setTenantId(reqCondition.getTenantId());
                    insertList.add(detailPO);
                }
            }
        }
        demandPlanMaterialDetailDao.batchInsertAndUpdate(insertList, updateList, null);
    }


    /**
     * 计算冲销值
     *
     * @param planMaterialParameterPO
     * @return
     */
    private Map<Date, Long> calculateReverseAmount(PlanMaterialParameterPO planMaterialParameterPO, List<DemandPlanMaterialDetailPO> planList, List<DemandPlanMaterialDetailPO> orderList) {

        /*if (CollectionUtils.isEmpty(planList)) {
            return Maps.newHashMap();
        }*/
        List<DemandPlanMaterialDetailPO> mergeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(planList)) {
            mergeList.addAll(planList);
        }
        if (CollectionUtils.isNotEmpty(orderList)) {
            mergeList.addAll(orderList);
        }
        if (CollectionUtils.isEmpty(mergeList)){
            return Maps.newHashMap();
        }
        Map<Date, Long> result = new HashMap<>();
        Date beginDate = mergeList.stream().filter(o -> o.getPlanDate() != null).map(DemandPlanMaterialDetailPO::getPlanDate).distinct().min((e1, e2) -> e1.compareTo(e2)).get();
        Date endDate = mergeList.stream().filter(o -> o.getPlanDate() != null).map(DemandPlanMaterialDetailPO::getPlanDate).distinct().max((e1, e2) -> e1.compareTo(e2)).get();

        Map<LocalDate, BigDecimal> materialOrderDemands = CollectionUtils.isEmpty(orderList) ? new HashMap<>() : orderList.stream().collect(Collectors.toMap(k -> DateUtils.toLocalDate(k.getPlanDate()), k -> Objects.isNull(k.getAmount()) ? new BigDecimal(0) : new BigDecimal(k.getAmount())));
        Map<LocalDate, BigDecimal> materialPredictDemands = CollectionUtils.isEmpty(planList) ? new HashMap<>() : planList.stream().collect(Collectors.toMap(k -> DateUtils.toLocalDate(k.getPlanDate()), k -> Objects.isNull(k.getAmount()) ? new BigDecimal(0) : new BigDecimal(k.getAmount())));

        MaterialFactor factor = new MaterialFactor();
        factor.setPlanStartDate(DateUtils.toLocalDate(beginDate));
        factor.setPlanEndDate(DateUtils.toLocalDate(endDate));
        factor.setMaterialCode(planMaterialParameterPO.getMaterialCode());
        factor.setLogicalPlantNo(planMaterialParameterPO.getLogicalPlantNo());
        factor.setTenantId(planMaterialParameterPO.getTenantId());
        DemandFactor demandFactor = new DemandFactor();
        demandFactor.setMaterialOrderDemands(materialOrderDemands);
        demandFactor.setMaterialPredictDemands(materialPredictDemands);
        factor.setDemandFactor(demandFactor);
        ParameterFactor parameterFactor = new ParameterFactor();
        parameterFactor.setDemandStrategyCode(planMaterialParameterPO.getDemandStrategyCode());
        parameterFactor.setPlanType(PlanMaterialParamPlanTypeEnum.getByCode(planMaterialParameterPO.getPlanType()));
        factor.setParameterFactor(parameterFactor);
        for (LocalDate planDate = factor.getPlanStartDate();
             !planDate.isAfter(factor.getPlanEndDate());
             planDate = planDate.plusDays(1)) {
            factor.setPlanDate(planDate);
            BigDecimal reverseAmount = grossRequirementFormula.apply(factor).value();
            //封装每一天的计算结果
            result.put(DateUtils.toDate(planDate), reverseAmount.longValue());
        }
        return result;
    }

    /**
     * 查询物料计划参数
     *
     * @param detailPOList
     * @return
     */
    private Map<String, PlanMaterialParameterPO> queryPlanMaterialParam(List<DemandPlanMaterialDetailReqCondition> detailPOList) {
        List<PlanMaterialParamQueryCondition> conditions = new ArrayList<>();
        for (DemandPlanMaterialDetailReqCondition po : detailPOList) {
            PlanMaterialParamQueryCondition queryCondition = new PlanMaterialParamQueryCondition();
            queryCondition.setMaterialCode(po.getMaterialCode());
            queryCondition.setLogicalPlantNo(po.getLogicalPlantNo());
            queryCondition.setTenantId(po.getTenantId());
            conditions.add(queryCondition);
        }
        conditions = conditions.stream().distinct().collect(Collectors.toList());
        List<PlanMaterialParameterPO> parameterPOList = planConfigDao.batchQueryPlanMaterialParam(conditions);
        Map<String, PlanMaterialParameterPO> parameterPOMap = parameterPOList.stream().collect
                (Collectors.toMap(k -> k.getMaterialCode() + CommonConstants.DELIMITER_COMMA + k.getLogicalPlantNo() + CommonConstants.DELIMITER_COMMA + k.getTenantId(),
                        v -> v, (v1, v2) -> v2));
        return parameterPOMap;
    }


}
