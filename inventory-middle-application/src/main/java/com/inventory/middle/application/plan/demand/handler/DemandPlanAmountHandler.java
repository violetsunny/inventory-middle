package com.inventory.middle.application.plan.demand.handler;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.DemandPlanMaterialDetailTypeEnum;
import com.inventory.middle.domain.plan.common.enums.MaterialSceneTypeEnum;
import com.inventory.middle.domain.plan.common.enums.MqTagEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.demand.convertor.DemandPlanAmountRequestConverter;
import com.inventory.middle.application.plan.mq.DefaultMqProducer;
import com.inventory.middle.application.plan.demand.message.DemandPlanMessage;
import com.inventory.middle.infra.plan.persistence.dao.DemandPlanMaterialDetailDao;
import com.inventory.middle.infra.plan.persistence.condition.plan.DemandPlanMaterialDetailReqCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.application.plan.support.ProductSupportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 针对数据变化做处理
 * @author:Vincent.Xiao
 * @date:2021/10/11 10:28
 */
@Component
@Slf4j
public class DemandPlanAmountHandler {

    @Autowired
    private DemandPlanMaterialDetailDao demandPlanMaterialDetailDao;

    @Autowired
    private InventorySupportService inventorySupportService;
    @Autowired
    private ProductSupportService productSupportService;

    @Resource
    private DefaultMqProducer defaultMqProducer;


    public void process(DemandPlanAmountRequestContext request) {

        log.info("DemandPlanAmountRequestContext request:{}", JSON.toJSONString(request));

        List<DemandPlanMaterialDetailReqCondition> reqList = DemandPlanAmountRequestConverter.INSTANCE.toConditionPOList(request.getChangeAmountList());

        //查询物料的需求明细
        List<DemandPlanMaterialDetailPO> planList = demandPlanMaterialDetailDao.findDetailsByCondition(reqList, true);

        Map<String, DemandPlanMaterialDetailPO> planDetailMap = planList.stream()
                .collect(Collectors.toMap(k -> k.getMaterialCode() + CommonConstants.DELIMITER_COMMA + k.getLogicalPlantNo() + CommonConstants.DELIMITER_COMMA + k.getTenantId() + CommonConstants.DELIMITER_COMMA + DateUtils.dateToString(k.getPlanDate(), DateUtils.YYYY_MM_DD_SLASH),
                        v -> v, (v1, v2) -> v2));

        Map<String, DemandPlanMaterialAmount> changeDetailMap = request.getChangeAmountList().stream()
                .collect(Collectors.toMap(k -> k.getMaterialCode() + CommonConstants.DELIMITER_COMMA + k.getLogicalPlantNo() + CommonConstants.DELIMITER_COMMA + k.getTenantId() + CommonConstants.DELIMITER_COMMA + DateUtils.dateToString(k.getPlanDate(), DateUtils.YYYY_MM_DD_SLASH),
                        v -> v, (v1, v2) -> v2));

        List<DemandPlanMaterialDetailPO> updateList = new ArrayList<>();
        List<DemandPlanMaterialDetailPO> insertList = new ArrayList<>();

        for (String key : changeDetailMap.keySet()) {
            DemandPlanMaterialAmount changeAmount = changeDetailMap.get(key);
            DemandPlanMaterialDetailPO temp = planDetailMap.get(key);
            if (Objects.nonNull(temp)) {
                long amount = temp.getAmount() + changeAmount.getChangeAmount();
                temp.setAmount(amount > 0 ? amount : 0);
                updateList.add(temp);
            } else if (changeAmount.getChangeAmount() > 0) {
                DemandPlanMaterialDetailPO detailPO = new DemandPlanMaterialDetailPO();
                detailPO = buildBaseInfo(changeAmount.getLogicalPlantNo(), changeAmount.getMaterialCode(), changeAmount.getTenantId(), detailPO);
                detailPO.setAmount(changeAmount.getChangeAmount());
                detailPO.setType(changeAmount.getType());
                detailPO.setPlanDate(changeAmount.getPlanDate());
                insertList.add(detailPO);
            }
        }

        //将相关的物料插入到物料表中，供后续查询使用

        List<PlanMaterialPO> planMaterials = new ArrayList<>();
        request.getChangeAmountList().forEach(demandPlanMaterialAmount -> {
            if (Objects.equals(demandPlanMaterialAmount.getType(), DemandPlanMaterialDetailTypeEnum.ORDER.getCode())
                    || (Objects.equals(demandPlanMaterialAmount.getType(), DemandPlanMaterialDetailTypeEnum.PLAN.getCode())
                    && demandPlanMaterialAmount.getChangeAmount() > 0)) {
                PlanMaterialPO materialPO = new PlanMaterialPO();
                materialPO.setMaterialCode(demandPlanMaterialAmount.getMaterialCode());
                materialPO.setLogicalPlantNo(demandPlanMaterialAmount.getLogicalPlantNo());
                materialPO.setTenantId(demandPlanMaterialAmount.getTenantId());
                materialPO.setSourceId(0L);
                materialPO.setType(MaterialSceneTypeEnum.DEMAND_INDEPENDENT_TYPE.getCode());
                if (Objects.nonNull(request.getDemandPlanPO())) {
                    materialPO.setCreatorId(request.getDemandPlanPO().getCreatorId());
                    materialPO.setUpdatorId(request.getDemandPlanPO().getUpdatorId());
                } else {
                    materialPO.setCreatorId("ORDER");
                    materialPO.setUpdatorId("ORDER");
                }
                planMaterials.add(materialPO);
            }
        });

        demandPlanMaterialDetailDao.batchInsertAndUpdate(insertList, updateList, planMaterials);
        //发送mq 消息，供需存那边需要用
        int type = request.getChangeAmountList().get(0).getType();
        if (type == DemandPlanMaterialDetailTypeEnum.ORDER.getCode()) {
            //订单是数据不发送mq
            return;
        }
        List<DemandPlanMaterialDetailPO> newPlanList = demandPlanMaterialDetailDao.findDetailsByCondition(reqList, true);
        sendMessage(request.getDemandPlanPO(), newPlanList, type);

    }

    public void sendMessage(DemandPlanPO demandPlanPO, List<DemandPlanMaterialDetailPO> newPlanList, int type) {
        try {
            //订单数据不需要发送mq了
            if (CollectionUtils.isEmpty(newPlanList)) {
                return;
            }
            List<DemandPlanMessage> demandPlanMessageList = new ArrayList<>();
            for (DemandPlanMaterialDetailPO detailPO : newPlanList) {
                DemandPlanMessage planMessage = new DemandPlanMessage();
                planMessage.setMaterialCode(detailPO.getMaterialCode());
                planMessage.setPlanDate(detailPO.getPlanDate());
                planMessage.setLogicalPlantNo(detailPO.getLogicalPlantNo());
                planMessage.setTenantId(detailPO.getTenantId());
                planMessage.setAmount(detailPO.getAmount());
                planMessage.setVoucherNo(demandPlanPO.getPlanVersion());
                demandPlanMessageList.add(planMessage);
            }
            defaultMqProducer.doSend(JSON.toJSONString(demandPlanMessageList), MqTagEnum.PLAN_DEMAND_TAG.getCode());
        } catch (Exception e) {
            log.error("demand plan sendMessage error,message:{}", JSON.toJSONString(newPlanList), e);
        }


    }

    /**
     * 构建基础信息
     *
     * @param detailPO
     * @param tenantId
     * @return
     */
    private DemandPlanMaterialDetailPO buildBaseInfo(String logicalPlantNo, String materialCode, String tenantId, DemandPlanMaterialDetailPO detailPO) {
        InvPlantBO logicalPlant = inventorySupportService.findPlant(logicalPlantNo, tenantId);
        ProductBO product = productSupportService.findProduct(materialCode, tenantId);
        detailPO.setMaterialCode(materialCode);
        detailPO.setMaterialDesc(Objects.isNull(product) ? null : product.getName());
        detailPO.setMaterialUnit(Objects.isNull(product) ? null : product.getUnit());
        detailPO.setLogicalPlantNo(logicalPlantNo);
        detailPO.setLogicalPlantName(Objects.isNull(logicalPlant) ? null : logicalPlant.getPlantName());
        detailPO.setTenantId(tenantId);
        return detailPO;
    }


}
