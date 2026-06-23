package com.inventory.middle.application.plan.plan.calculate.support.generate.processor.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.plan.common.enums.MessageSourceTypeEnum;
import com.inventory.middle.domain.plan.common.enums.MqTagEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanOrderExtInfoKeyEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.mq.DefaultMqProducer;
import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanDetailBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanInstanceBO;
import com.inventory.middle.domain.plan.order.bo.PlanOrderBomRelationDemandBO;
import com.inventory.middle.domain.plan.order.bo.SystemPlanOrderCreateBO;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.application.plan.plan.calculate.support.generate.PlanGeneType;
import com.inventory.middle.application.plan.plan.calculate.support.report.collector.CollectRange;
import com.inventory.middle.application.plan.plan.calculate.support.report.collector.CollectRangeCreator;
import com.inventory.middle.application.plan.plan.calculate.support.report.collector.FixIntervalCollectRangeCreator;
import com.inventory.middle.infra.plan.persistence.dao.plan.DemandSupplySourceDao;
import com.inventory.middle.infra.plan.persistence.condition.plan.DemandSourceForPlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandSupplySourcePO;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.plan.common.enums.MaterialPlanDetailBusinessExtKeyEnum.*;
import static com.inventory.middle.domain.plan.common.enums.MaterialPlanExtKeyEnum.*;

/**
 * 物料计划实例后置处理-计划订单创建
 *
 * @author Danny.Lee
 * @date 2021/11/3
 */
@Slf4j
@Component
public class MpiPlanOrderCreatePostProcessor
//        implements MaterialPlanInstancePostProcessor
{

    @Resource
    private DemandSupplySourceDao demandSupplySourceDao;

    @Resource
    private DefaultMqProducer defaultMqProducer;

    //    @Override
    public void postProcess(PlanInstanceBO planInstance, MaterialPlanInstanceBO materialPlanInstance,
                            Set<MaterialBO> rootMaterials, Map<LocalDate, Collection<PlanOrderBomRelationDemandBO>> correlatedDemands) {
        if (MapUtils.isEmpty(materialPlanInstance.getMaterialPlanDetails())) {
            return;
        }

        // 物料计划详情汇总处理
        // 周期类型物料计划需要根据订货周期将对应周期内的指标进行汇总
        List<MaterialPlanDetailWithDemandInfo> collectedDetails = this.collectDetailsIfNecessary(materialPlanInstance, rootMaterials);

        // 单条执行
        for (MaterialPlanDetailWithDemandInfo detail : collectedDetails) {
            // check
            if (!supportCreatePlanOrder(detail)) {
                return;
            }
            try {
                SystemPlanOrderCreateBO systemPlanOrderCreateBO = this.buildPlanOrderRequest(planInstance, materialPlanInstance, detail, correlatedDemands);
                defaultMqProducer.doSend(JSONObject.toJSONString(systemPlanOrderCreateBO), MqTagEnum.SYSTEM_PLAN_ORDER_CREATE_TAG.getCode());
            } catch (Exception ex) {
                // 添加指标监控MONITOR
                log.error("create plan order error|detail={}", detail, ex);
            }
        }
    }

    private List<MaterialPlanDetailWithDemandInfo> convertDetailWithDemands(MaterialPlanInstanceBO mpInstance, Set<MaterialBO> rootMaterials) {
        List<MaterialPlanDetailWithDemandInfo> decorators = Lists.newArrayListWithCapacity(mpInstance.getMaterialPlanDetails().size());

        // 物料计划详情排序
        List<MaterialPlanDetailBO> details = Lists.newArrayList(mpInstance.getMaterialPlanDetails().values());
        details.sort(Comparator.comparing(MaterialPlanDetailBO::getPlanDate));

        // 查询物料对应需求(预测&订单)
        final Date retrieveStartDate = details.get(0).getPlanDate();
        final Date retrieveEndDate = details.get(details.size() - 1).getPlanDate();

        Map<LocalDate, Collection<PlanOrderDemand>> planOrderDemands = this.fetchPlanOrderDemands(
                mpInstance, rootMaterials, retrieveStartDate, retrieveEndDate);

        for (MaterialPlanDetailBO detail : details) {
            decorators.add(copy(detail, planOrderDemands));
        }
        return decorators;
    }

    /**
     * 查询计划订单页面需求信息:负责从供需存看板中获取需求信息，需求信息包括以下内容:
     * <ul>
     *     <li>独立物料对应的预测需求</li>
     *     <li>独立物料对应的订单需求</li>
     *     <li>相关物料对应的根节点(母件)的预测需求</li>
     *     <li>相关物料对应的根节点(母件)的订单需求</li>
     * </ul>
     * @param mpInstance
     * @param rootMaterials
     * @param planDateStart
     * @param planDateEnd
     * @return
     */
    private Map<LocalDate, Collection<PlanOrderDemand>> fetchPlanOrderDemands(MaterialPlanInstanceBO mpInstance, Set<MaterialBO> rootMaterials,
                                                                              Date planDateStart, Date planDateEnd) {
        List<String> materialCodesToRetrieve = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(rootMaterials)) {
            materialCodesToRetrieve.addAll(rootMaterials.stream().map(MaterialBO::getMaterialCode).collect(Collectors.toList()));
        }
        materialCodesToRetrieve.add(mpInstance.getMaterialCode());

        DemandSourceForPlanOrderCondition condition = new DemandSourceForPlanOrderCondition();
        condition.setMaterialCodes(materialCodesToRetrieve);
        condition.setLogicalPlantNo(mpInstance.getLogicalPlantNo());
        condition.setTenantId(mpInstance.getTenantId());
        condition.setSourceTypes(Lists.newArrayList(MessageSourceTypeEnum.PLAN_DEMAND.getCode(), MessageSourceTypeEnum.CUSTOMER_ORDER_DEMAND.getCode()));
        condition.setPlanDateStart(planDateStart);
        condition.setPlanDateEnd(planDateEnd);

        List<DemandSupplySourcePO> demandSupplySources = demandSupplySourceDao.queryForPlanOrderDemands(condition);

        Multimap<LocalDate, PlanOrderDemand> planOrderDemands = HashMultimap.create();
        for (DemandSupplySourcePO demandSupplySource : demandSupplySources) {
            final LocalDate date = DateUtils.toLocalDate(demandSupplySource.getPlanDate());
            // source type is same as PlanOrderDemandSourceTypeEnum
            planOrderDemands.put(date, PlanOrderDemand.of(demandSupplySource.getSourceType(), demandSupplySource.getVoucherNo(), demandSupplySource.getDocumentNo()));
        }
        return planOrderDemands.asMap();
    }

    private MaterialPlanDetailWithDemandInfo copy(MaterialPlanDetailBO detail, Map<LocalDate, Collection<PlanOrderDemand>> planOrderDemands) {
        MaterialPlanDetailWithDemandInfo pack = new MaterialPlanDetailWithDemandInfo();
        MaterialPlanDetailBO copy = detail.copyBasic();

        // 忽略计划订单产出的物料计划详情需要清空指标-用于移除对应日期的计划订单
        if (!this.ignoreToCreatePlanOrder(detail)) {
            Optional.ofNullable(detail.getIndicators()).ifPresent(indicators -> copy.setIndicators(Maps.newHashMap(indicators)));
            Optional.ofNullable(detail.getBusinessExtAttrs()).ifPresent(extAttrs -> copy.setBusinessExtAttrs(Maps.newHashMap(extAttrs)));
            Collection<PlanOrderDemand> planOrderDemandsToAppend = planOrderDemands.get(DateUtils.toLocalDate(detail.getPlanDate()));
            if (CollectionUtils.isNotEmpty(planOrderDemandsToAppend)) {
                pack.demands.addAll(planOrderDemandsToAppend);
            }
        }

        pack.detail = copy;
        return pack;
    }

    private boolean ignoreToCreatePlanOrder(MaterialPlanDetailBO detail) {
        return StringUtils.equalsAnyIgnoreCase("true", detail.getBusinessExt(IGNORE_PLAN_ORDER.getCode()));
    }

    private List<MaterialPlanDetailWithDemandInfo> collectDetailsIfNecessary(MaterialPlanInstanceBO materialPlanInstance, Set<MaterialBO> rootMaterials) {
        List<MaterialPlanDetailWithDemandInfo> detailWithDemands = this.convertDetailWithDemands(materialPlanInstance, rootMaterials);
        // 非周期类型的物料计划无需整合处理
        if (!needCollectDetails(materialPlanInstance)) {
            return detailWithDemands;
        }

        JSONObject extAttrs = materialPlanInstance.getExtAttrs();
        Integer orderCycleTime = extAttrs.getInteger(ORDER_CYCLE_TIME.getCode());

        LocalDate prime = this.findPrimeDate(materialPlanInstance, detailWithDemands);
        CollectRangeCreator creator = new FixIntervalCollectRangeCreator(orderCycleTime);
        // 根据订货周期进行详情分组
        Map<CollectRange, List<MaterialPlanDetailWithDemandInfo>> groupDetails = detailWithDemands.stream()
                .collect(Collectors.groupingBy(detail -> creator.range(DateUtils.toLocalDate(detail.detail.getPlanDate()), prime),
                        Maps::newTreeMap, Collectors.toList()));

        DetailCollector detailCollector = this.decideDetailCollector(materialPlanInstance);
        for (Map.Entry<CollectRange, List<MaterialPlanDetailWithDemandInfo>> groupDetail : groupDetails.entrySet()) {
            detailCollector.collect(groupDetail.getValue());
        }

        return detailWithDemands;
    }

    private DetailCollector decideDetailCollector(MaterialPlanInstanceBO materialPlanInstance) {
        JSONObject extAttrs = materialPlanInstance.getExtAttrs();
        Integer planType = extAttrs.getInteger(MATERIAL_PLAN_TYPE.getCode());
        // 是否调拨类型
        return null != planType && Objects.equals(planType, PlanMaterialParamPlanTypeEnum.TRANSFER.getCode()) ?
                TransferDetailCollector.INSTANCE : NoTransferDetailCollector.INSTANCE;
    }

    interface DetailCollector {
        /**
         * 合并物料计划详情
         *
         * @param detailsToCollect 物料计划详情
         */
        void collect(List<MaterialPlanDetailWithDemandInfo> detailsToCollect);
    }

    /**
     * 非调拨场景-物料计划详情合并器<br/>
     * 首元素(index=0)汇总集合内所有指标，其他元素指标置为0
     */
    static class NoTransferDetailCollector implements DetailCollector {

        static NoTransferDetailCollector INSTANCE = new NoTransferDetailCollector();

        @Override
        public void collect(List<MaterialPlanDetailWithDemandInfo> detailsToCollect) {
            MaterialPlanDetailWithDemandInfo prime = null;
            for (int i = 0; i < detailsToCollect.size(); i++) {
                MaterialPlanDetailWithDemandInfo detail = detailsToCollect.get(i);
                if (i == 0) {
                    prime = detail;
                } else {
                    collectDetail(prime, detail);
                }
            }
        }
    }

    private static void collectDetail(MaterialPlanDetailWithDemandInfo prime, MaterialPlanDetailWithDemandInfo toCollectDetail) {
        // 指标汇总
        toCollectDetail.detail.getIndicators().forEach((code, value) -> {
            BigDecimal indicator = Optional.ofNullable(prime.detail.getIndicators())
                    .map(indicators -> indicators.get(code))
                    .orElse(BigDecimal.ZERO);

            prime.detail.appendIndicator(code, indicator.add(value));
        });
        // 需求要素汇总
        prime.demands.addAll(toCollectDetail.demands);

        // 清空指标
        toCollectDetail.detail.getIndicators().clear();
        // 清空需求要素
        toCollectDetail.demands.clear();
    }

    static class TransferDetailCollector implements DetailCollector {

        static TransferDetailCollector INSTANCE = new TransferDetailCollector();

        @Override
        public void collect(List<MaterialPlanDetailWithDemandInfo> detailsToCollect) {
            Map<String, MaterialPlanDetailWithDemandInfo> groupDetails = Maps.newHashMap();
            for (MaterialPlanDetailWithDemandInfo detailToCollect : detailsToCollect) {
                // 根据调拨仓进行分组
                final String transPlant = detailToCollect.detail.getBusinessExt(TRANSFER_PLANT.getCode());
                if (groupDetails.containsKey(transPlant)) {
                    // 选取日期较早的物料计划详情进行指标合并
                    final MaterialPlanDetailWithDemandInfo collectDetail = groupDetails.get(transPlant);
                    final Date collectDetailDate = collectDetail.detail.getPlanDate();
                    final Date copyThatDate = detailToCollect.detail.getPlanDate();
                    if (copyThatDate.before(collectDetailDate)) {
                        // 合并指标
                        collectDetail(detailToCollect, collectDetail);
                        // 汇总指标的物料计划详情进行替换
                        groupDetails.put(transPlant, detailToCollect);
                    } else {
                        collectDetail(collectDetail, detailToCollect);
                    }
                } else {
                    groupDetails.put(transPlant, detailToCollect);
                }

            }
        }
    }

    private LocalDate findPrimeDate(MaterialPlanInstanceBO materialPlanInstance, List<MaterialPlanDetailWithDemandInfo> detailWithDemands) {
        LocalDate prime = null;
        if (null != materialPlanInstance.getExtAttrs()) {
            prime = materialPlanInstance.getExtAttrs().getObject(START_DATE.getCode(), LocalDate.class);
        }
        if (null == prime) {
            prime = DateUtils.toLocalDate(detailWithDemands.get(0).detail.getPlanDate());
        }
        return prime;
    }

    /**
     * 是否需要汇总物料计划详情
     *
     * @param materialPlanInstance 物料计划实例
     * @return true表示需要汇总
     */
    private boolean needCollectDetails(MaterialPlanInstanceBO materialPlanInstance) {
        JSONObject extAttrs = materialPlanInstance.getExtAttrs();
        if (null == extAttrs) {
            return false;
        }
        if (!StringUtils.equals(PlanGeneType.JOB.name(), extAttrs.getString(GENE_TYPE.getCode()))) {
            return false;
        }
        Integer orderCycleTime = extAttrs.getInteger(ORDER_CYCLE_TIME.getCode());
        return null != orderCycleTime && orderCycleTime > 0;
    }

    /**
     * 是否支持创建计划订单
     *
     * @param detail 物料计划详情
     * @return true表示支持创建
     */
    private boolean supportCreatePlanOrder(MaterialPlanDetailWithDemandInfo detail) {
        return true;
    }

    /**
     * 构建计划订单创建请求
     *
     * @param planInstance         计划实例
     * @param materialPlanInstance 物料计划实例
     * @param detailWithDemandInfo 物料计划详情
     * @param correlatedDemands    物料对应相关实例
     * @return 计划订单创建请求
     */
    private SystemPlanOrderCreateBO buildPlanOrderRequest(PlanInstanceBO planInstance,
                                                          MaterialPlanInstanceBO materialPlanInstance,
                                                          MaterialPlanDetailWithDemandInfo detailWithDemandInfo,
                                                          Map<LocalDate, Collection<PlanOrderBomRelationDemandBO>> correlatedDemands) {
        final MaterialPlanDetailBO detail = detailWithDemandInfo.detail;
        final List<PlanOrderDemand> demands = detailWithDemandInfo.demands;
        SystemPlanOrderCreateBO request = new SystemPlanOrderCreateBO();
        request.setUserId(detail.getCreatorId());
        request.setTenantId(detail.getTenantId());

        request.setPlanId(planInstance.getPlanId());
        request.setMaterialCode(detail.getMaterialCode());
        request.setLogicalPlantNo(detail.getLogicalPlantNo());
        request.setTransferLogicalPlantNo(this.findTransferPlant(detail));

        final Map<String, BigDecimal> indicators = detail.getIndicators();
        int planProduceAmount = Optional.ofNullable(MapUtils.getObject(indicators, Indicators.PLAN_PRODUCE.getIndicatorCode()))
                .map(BigDecimal::intValue).orElse(0);
        int forecastAmount = Optional.ofNullable(MapUtils.getObject(indicators, Indicators.NET_REQUIREMENT.getIndicatorCode()))
                .map(BigDecimal::intValue).orElse(0);
        request.setPlanOrderQuantity(planProduceAmount);
        request.setForecastInventory(forecastAmount);

        request.setPlanReceivingTime(DateUtils.getEndOfDay(detail.getPlanDate()));

        // 计算下发日期=接收日期-提前期
        // 若下发日期早于当前日期，则会创建逾期的计划订单
        LocalDate receivingDate = DateUtils.toLocalDate(detail.getPlanDate());
        int materialVendorLeadTime = this.calculateVendorLeadTime(materialPlanInstance, detail);
        LocalDate issueDate = receivingDate.minusDays(materialVendorLeadTime);
        request.setPlanIssueTime(DateUtils.getEndOfDay(DateUtils.toDate(issueDate)));

        // 需求信息
        request.setDemandInfo(new JSONArray().fluentAddAll(demands));

        // 扩展信息
        // 子件相关需求
        if (MapUtils.isNotEmpty(correlatedDemands) && CollectionUtils.isNotEmpty(correlatedDemands.get(receivingDate))) {
            request.setExtAttrs(new JSONObject().fluentPut(PlanOrderExtInfoKeyEnum.BOM_RELATION_DEMAND.getCode(), correlatedDemands.get(receivingDate)));
        }

        return request;
    }

    private int calculateVendorLeadTime(MaterialPlanInstanceBO materialPlanInstance, MaterialPlanDetailBO detail) {
        Integer vendorLeadTime = Optional.ofNullable(
                materialPlanInstance.getExtAttrs().getInteger(VENDOR_LEAD_TIME.getCode())).orElse(0);
        // 物料提前期 + 调拨仓库提前期(@see MpdReCalculateForTransferPostProcessor)
        return vendorLeadTime + this.findTransferPlantVendorLeadTime(detail);
    }

    private String findTransferPlant(MaterialPlanDetailBO detail) {
        return detail.getBusinessExt(TRANSFER_PLANT.getCode());
    }

    private Integer findTransferPlantVendorLeadTime(MaterialPlanDetailBO detail) {
        String transferVendorLeadTime = detail.getBusinessExt(TRANSFER_VENDOR_LEAD_TIME.getCode());
        return Optional.ofNullable(transferVendorLeadTime)
                .map(Integer::parseInt)
                .orElse(0);
    }

    @Data
    private static class PlanOrderDemand {

        private Integer demandType;

        private String demandOrderNo;

        private String demandSubOrderNo;

        public static PlanOrderDemand of(Integer demandType, String demandOrderNo, String demandSubOrderNo) {
            PlanOrderDemand planOrderDemand = new PlanOrderDemand();
            planOrderDemand.setDemandType(demandType);
            planOrderDemand.setDemandOrderNo(demandOrderNo);
            planOrderDemand.setDemandSubOrderNo(demandSubOrderNo);
            return planOrderDemand;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PlanOrderDemand that = (PlanOrderDemand) o;
            return demandType.equals(that.demandType) && demandOrderNo.equals(that.demandOrderNo) && Objects.equals(demandSubOrderNo, that.demandSubOrderNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(demandType, demandOrderNo, demandSubOrderNo);
        }
    }

    /**
     * 物料计划详情
     */
    private static class MaterialPlanDetailWithDemandInfo {
        private MaterialPlanDetailBO detail;
        private final List<PlanOrderDemand> demands = Lists.newArrayList();
    }
}
