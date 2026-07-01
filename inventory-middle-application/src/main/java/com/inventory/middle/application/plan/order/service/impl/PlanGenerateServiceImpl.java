package com.inventory.middle.application.plan.order.service.impl;

import top.kdla.framework.validator.BaseAssert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.enums.InstanceStatusEnum;
import com.inventory.middle.domain.plan.common.enums.MaterialPlanDetailNsExtKeyEnum;
import com.inventory.middle.domain.plan.common.enums.IndefiniteQuantityStrategyEnum;
import com.inventory.middle.domain.plan.common.enums.OrderStrategyEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanProduceEnum;
import com.inventory.middle.domain.plan.common.enums.PlanQuantityStrategyEnum;
import com.inventory.middle.domain.plan.common.enums.PlanRelatedBomEnum;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.domain.plan.common.ex.PlanGeneError;
import com.inventory.middle.domain.plan.common.rule.IValidatorChain;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.bom.bo.BomTreeBO;
import com.inventory.middle.domain.plan.bom.bo.BomTreeRenderRequestBO;
import com.inventory.middle.domain.plan.bom.service.BomCaseService;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.calculate.bo.*;
import com.inventory.middle.application.plan.calculate.bo.report.*;
import com.inventory.middle.application.plan.calculate.config.PlanExecutorConfig;
import com.inventory.middle.application.plan.calculate.convert.PlanGenerateConverter;
import com.inventory.middle.application.plan.calculate.service.PlanGenerateService;
import com.inventory.middle.application.plan.calculate.support.formula.Formula;
import com.inventory.middle.application.plan.calculate.support.formula.FormulaContext;
import com.inventory.middle.application.plan.calculate.support.formula.Formulas;
import com.inventory.middle.application.plan.calculate.support.formula.factor.FactorHelper;
import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.application.plan.calculate.support.generate.BomMaterialPlanGeneContext;
import com.inventory.middle.application.plan.calculate.support.generate.MaterialPlanGeneContext;
import com.inventory.middle.application.plan.calculate.support.generate.PlanGeneType;
import com.inventory.middle.application.plan.calculate.support.generate.processor.MaterialPlanDetailPostProcessor;
import com.inventory.middle.application.plan.calculate.support.generate.processor.MaterialPlanInstancePostProcessor;
import com.inventory.middle.application.plan.calculate.support.generate.processor.impl.MpiPlanOrderCreatePostProcessor;
import com.inventory.middle.application.plan.calculate.support.generate.validator.model.PlanGeneValidateResponse;
import com.inventory.middle.application.plan.calculate.support.report.collector.CollectRange;
import com.inventory.middle.application.plan.calculate.support.report.collector.CollectType;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamQueryReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.config.enums.*;
import com.inventory.middle.application.plan.config.service.PlanConfigService;
import com.inventory.middle.domain.plan.order.bo.PlanOrderBomRelationDemandBO;
import com.inventory.middle.infra.plan.persistence.dao.MaterialPlanDetailDao;
import com.inventory.middle.infra.plan.persistence.dao.MaterialPlanInstanceDao;
import com.inventory.middle.infra.plan.persistence.dao.PlanInstanceDao;
import com.inventory.middle.infra.plan.persistence.condition.MaterialPlanInstanceCondition;
import com.inventory.middle.infra.plan.persistence.condition.PageCondition;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstancePO;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstanceWithPlanPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanInstancePO;
import com.inventory.middle.infra.plan.sequence.Sequence;
import com.inventory.middle.infra.plan.sequence.SequenceFactory;
import com.inventory.middle.infra.plan.stub.PlanParticipantStub;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.application.plan.support.ProductSupportService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.PagedSingleResponse;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.plan.common.constants.CommonConstants.PLAN_VERSION_SEQUENCE_PREFIX;
import static com.inventory.middle.domain.plan.common.enums.MaterialPlanExtKeyEnum.*;
import static com.inventory.middle.domain.common.constants.ResponseCodeEnum.PI_REPORT_NOT_EXIST;

/**
 * 计划子域执行服务实现
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Slf4j
@Service
public class PlanGenerateServiceImpl implements PlanGenerateService {

    @Resource
    private PlanInstanceDao planInstanceDao;

    @Resource
    private MaterialPlanDetailDao materialPlanDetailDao;

    @Resource
    private MaterialPlanInstanceDao materialPlanInstanceDao;

    @Resource
    private PlanGenerateConverter planGenerateConverter;

    @Resource
    private FactorHelper factorHelper;

    @Resource
    private SequenceFactory sequenceFactory;

    @Resource
    private IValidatorChain<?, ?> planGeneValidatorChain;

    @Resource
    private BomCaseService bomCaseService;

    @Resource
    private PlanConfigService planConfigService;

    @Resource
    private ProductSupportService productSupportService;

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private PlanParticipantStub planParticipantStub;

    @Resource
    @Qualifier(PlanExecutorConfig.PLAN_CALCULATE_EXECUTOR)
    private ExecutorService planCalculateExecutor;

    @Resource
    private List<MaterialPlanInstancePostProcessor> materialPlanInstancePostProcessors;

    @Resource
    private List<MaterialPlanDetailPostProcessor> materialPlanDetailPostProcessors;

    @Resource
    private MpiPlanOrderCreatePostProcessor processor;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PlanInstanceBO generate(PlanGenerateRequestBO request) {
        // 1.准入校验
        ValidateMessage validateRequest = ValidateMessage.builder().t(request).build();
        ValidateMessage validationResponse = planGeneValidatorChain.doProcess(validateRequest);
        PlanGeneValidateResponse response = ((PlanGeneValidateResponse) validationResponse.getE());
        BaseAssert.isTrue(validationResponse.isSuccess(), response.getResponseCode().getDesc());

        final PlanBO plan = response.getPlan();
        final Map<MaterialBO, PlanMaterialParameterBO> materialWithParameters = response.getMaterialWithParameters();

        // 2.初始化计划实例(处理中)
        PlanInstanceBO planInstance = initPlanInstance(plan);
        PlanInstanceBomRelationBO planInstanceBomRelations = new PlanInstanceBomRelationBO();

        // 3.遍历计划物料，并行产出计划报表
        List<CompletableFuture<Void>> futures = Lists.newArrayList();
        // 遍历物料信息
        for (Map.Entry<MaterialBO, PlanMaterialParameterBO> materialWithParameter : materialWithParameters.entrySet()) {
            final MaterialBO material = materialWithParameter.getKey();
            final PlanMaterialParameterBO parameter = materialWithParameter.getValue();
            // 并行以提高性能
            futures.add(CompletableFuture.runAsync(() -> {
                // 构建物料计划上下文
                MaterialPlanGeneContext context =
                        buildMaterialPlanContext(material, plan, parameter, request.getGenerateType());

                // 计算物料计划
                MaterialPlanInstanceBO materialPlanInstance = generateForMaterial(planInstance, context);

                planInstance.appendMaterialPlanInstance(materialPlanInstance);

                // Bom处理
                processSubordinateMaterials(request, plan, planInstance, materialPlanInstance, planInstanceBomRelations);

            }, planCalculateExecutor));
        }

        CompletableFuture future = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenCompleteAsync((ret, ex) -> {
                    // 后置处理计划实例
                    postProcessPlanInstance(planInstance);

                    // 保存计划实例
                    savePlanInstance(planInstance);

                    // 创建计划订单
                    createPlanOrder(planInstance, planInstanceBomRelations);

                }, planCalculateExecutor);

        if (PlanGeneType.MANUAL != request.getGenerateType()) {
            future.join();
        }

        return planInstance;
    }

    private void postProcessPlanInstance(PlanInstanceBO planInstance) {
        // do nothing
    }

    /**
     * 处理BOM下级物料
     *
     * @param request                  计划报表产出请求
     * @param plan                     计划方案
     * @param planInstance             计划实例
     * @param materialPlan             物料计划
     * @param planInstanceBomRelations 计划实例物料Bom关系
     */
    private void processSubordinateMaterials(PlanGenerateRequestBO request, PlanBO plan, PlanInstanceBO planInstance,
                                             MaterialPlanInstanceBO materialPlan, PlanInstanceBomRelationBO planInstanceBomRelations) {

        // 物料清单启用开关
//        if (!Boolean.parseBoolean(serviceConfig.getConfig("switch.enableBom", "false"))) {
//            return;
//        }
        // 判断物料计划有效性
        if (InstanceStatusEnum.isFail(materialPlan.getStatus())) {
            return;
        }
        // 物料计划类型 == 生产
        if (PlanMaterialParamPlanTypeEnum.getByCode(materialPlan.getExtAttrs().getInteger(MATERIAL_PLAN_TYPE.getCode()))
                != PlanMaterialParamPlanTypeEnum.PRODUCER) {
            return;
        }
        // 是否启用Bom
        if (!Boolean.TRUE.equals(materialPlan.getExtAttrs().getBoolean(ENABLE_BOM.getCode()))) {
            return;
        }

        // 查询Bom Tree
        BomTreeRenderRequestBO bomRenderRequest = new BomTreeRenderRequestBO();
        bomRenderRequest.setTenantId(materialPlan.getTenantId());
        bomRenderRequest.setMaterialCode(materialPlan.getMaterialCode());
        bomRenderRequest.setLogicalPlantNo(materialPlan.getLogicalPlantNo());
        bomRenderRequest.setShowLeaf(Boolean.TRUE);
        bomRenderRequest.setNodeAsRoot(Boolean.TRUE);
        bomRenderRequest.setShowEnable(Boolean.TRUE);
        List<BomTreeBO> bomTrees = bomCaseService.renderBomTree(bomRenderRequest);
        if (CollectionUtils.isEmpty(bomTrees)) {
            return;
        }

        final MaterialBO root = MaterialBO.of(materialPlan.getMaterialCode(), materialPlan.getLogicalPlantNo(), materialPlan.getTenantId());
        // 物料计划实例缓存-用于遍历BOM树节点时查找对应母件物料计划实例
        final Map<MaterialBO, MaterialPlanInstanceBO> materialPlans = Maps.newHashMap();
        // set bom info of root
        materialPlans.put(root, materialPlan);

        // 物料计划节点缓存-用于遍历BOM树时构建BOM树节点
        final Map<MaterialBO, MaterialPlanInstanceBomNodeBO> materialNodes = Maps.newHashMap();
        BomTreeBO bomTree = bomTrees.get(0);

        // 遍历Bom节点
        Optional.ofNullable(bomTree).ifPresent(tree -> tree.visit((parent, node) -> {
            if (null == parent) {
                // 跳过root节点处理
                materialNodes.put(root, MaterialPlanInstanceBomNodeBO.success(root, node.getMaterialDesc(), materialPlan.getId()));
                return;
            }
            final MaterialBO material = MaterialBO.of(node.getMaterialCode(), node.getLogicalPlantNo(), node.getTenantId());
            final MaterialBO parentMaterial = MaterialBO.of(parent.getMaterialCode(), parent.getLogicalPlantNo(), parent.getTenantId());
            MaterialPlanInstanceBO parentMaterialPlan = materialPlans.get(parentMaterial);
            MaterialPlanInstanceBomNodeBO parentNode = materialNodes.get(parentMaterial);
            if (null == parentMaterialPlan) {
                MaterialPlanInstanceBomNodeBO childNode = MaterialPlanInstanceBomNodeBO.fail(material, node.getMaterialDesc(), PlanGeneError.PARENT_NODE_NOT_EXIST);
                parentNode.addChildNode(childNode);
                materialNodes.put(material, childNode);
                return;
            }

            // 1.母件物料计划实例状态成功
            // 使用子件计划方案
            final PlanMaterialParameterBO parameter = findMaterialParameter(material);
            if (null == parameter) {
                MaterialPlanInstanceBomNodeBO childNode = MaterialPlanInstanceBomNodeBO.fail(material, node.getMaterialDesc(), PlanGeneError.PARAMETER_NOT_EXIST);
                parentNode.addChildNode(childNode);
                materialNodes.put(material, childNode);
                return;
            }

            // 2.构建物料计划产出上下文
            BomMaterialPlanGeneContext context = buildBomMaterialPlanContext(
                    buildMaterialPlanContext(material, plan, parameter, request.getGenerateType()),
                    node.getTreeAmount()/parent.getTreeAmount(), materialPlan, parentMaterialPlan);

            // 3.创建物料计划实例
            MaterialPlanInstanceBO childMaterialPlan = generateForMaterial(planInstance, context);

            // 4.维护BOMTree节点关系
            MaterialPlanInstanceBomNodeBO childNode = MaterialPlanInstanceBomNodeBO.of(childMaterialPlan, node.getMaterialDesc());
            parentNode.addChildNode(childNode);
            materialNodes.put(material, childNode);

            // 5.维护物料计划实例缓存-用于辅助创建子件物料计划实例
            materialPlans.put(material, childMaterialPlan);

            // 6.维护计划实例物料层级关系-用于辅助物料计划订单创建
            planInstanceBomRelations.addRoot(material, root);
            planInstanceBomRelations.addSubordinate(parentMaterial, material);


            planInstance.appendMaterialPlanInstance(childMaterialPlan);

        }));

        // 设置root_id
        materialPlan.setRootId(materialPlan.getId());
        // root节点设置BOM树结构，用于页面渲染使用
        materialPlan.getExtAttrs().put(BOM_TREE.getCode(), materialNodes.get(root));
        materialPlanInstanceDao.update(planGenerateConverter.convertMaterialPlanInstance(materialPlan));
    }

    /**
     * 初始化计划实例
     *
     * @param plan    计划方案
     * @return 计划实例
     */
    private PlanInstanceBO initPlanInstance(PlanBO plan) {
        // 初始化计划实例,状态=处理中
        PlanInstanceBO planInstance = PlanInstanceBO.initWithStatus(plan, InstanceStatusEnum.PROCESSING);

        // 设置计划员
        planInstance.setCreatorId(this.decideOperator(plan));
        planInstance.setCreateUserName(planParticipantStub.getUserName(planInstance.getCreatorId()));

        // 获取物料计划报表版本号id=yyyyMMdd+(8位Sequence)
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        Sequence sequence = sequenceFactory.getSequence(PLAN_VERSION_SEQUENCE_PREFIX + date);
        planInstance.setPlanVersion(date + StringUtils.leftPad(sequence.next() + "", 8, "0"));
        // 模型转换 & 落库数据
        PlanInstancePO planInstanceToSave = planGenerateConverter.convertPlanInstance(planInstance);
        planInstanceDao.insert(planInstanceToSave);
        planInstance.setId(planInstanceToSave.getId());
        return planInstance;
    }

    /**
     * 保存物料计划实例&详情
     *
     * @param materialPlanInstance 物料计划实例
     */
    private void saveMaterialPlanInstance(MaterialPlanInstanceBO materialPlanInstance) {
        MaterialPlanInstancePO materialPlanInstanceToSave =
                planGenerateConverter.convertMaterialPlanInstance(materialPlanInstance);

        List<MaterialPlanDetailPO> materialPlanDetails =
                planGenerateConverter.convertMaterialPlanDetails(
                        Lists.newArrayList(materialPlanInstance.getMaterialPlanDetails().values()));

        materialPlanInstanceDao.saveMaterialPlanInstance(materialPlanInstanceToSave, materialPlanDetails);
        materialPlanInstance.setId(materialPlanInstanceToSave.getId());
    }

    /**
     * 保存计划实例
     *
     * @param planInstance 计划实例
     */
    private void savePlanInstance(PlanInstanceBO planInstance) {
        planInstance.setCalEndTime(new Date());
        Integer piStatus = InstanceStatusEnum.SUCCESS.getCode();
        // 集合为空时 或 存在物料计划实例执行失败时，计划实例失败
        if (CollectionUtils.isEmpty(planInstance.getMaterialPlanInstances()) ||
                planInstance.getMaterialPlanInstances().stream()
                        .anyMatch(item -> InstanceStatusEnum.isFail(item.getStatus()))) {
            piStatus = InstanceStatusEnum.FAIL.getCode();
        }
        planInstance.setStatus(piStatus);
        planInstanceDao.update(planGenerateConverter.convertPlanInstance(planInstance));
    }

    /**
     * 创建计划订单
     *
     * @param planInstance 计划实例
     * @param relations    计划实例物料Bom关系
     */
    private void createPlanOrder(PlanInstanceBO planInstance, PlanInstanceBomRelationBO relations) {
        if (CollectionUtils.isEmpty(planInstance.getMaterialPlanInstances())) {
            return;
        }

        // 合并物料计划
        Map<MaterialBO, MaterialPlanInstanceBO> collectMpInstances = collectMaterialPlanForSameMaterial(planInstance);
        for (Map.Entry<MaterialBO, MaterialPlanInstanceBO> entry : collectMpInstances.entrySet()) {
            final MaterialBO material = entry.getKey();
            final MaterialPlanInstanceBO collectMpInstance = entry.getValue();
            try{
                processor.postProcess(planInstance, collectMpInstance, relations.rootMaterialCodes(material),
                        this.findCorrelatedDemands(relations.subordinateMaterialCodes(material), collectMpInstances));
            }catch (Exception e){
                log.error("create plan order error|material={}", material, e);
            }
        }

    }

    /**
     * 查询相关需求
     *
     * @param subordinateMaterials 子件物料编码
     * @param collectMpInstances   整合后的物料计划
     * @return 按日期分组的计划订单相关需求
     */
    private Map<LocalDate, Collection<PlanOrderBomRelationDemandBO>> findCorrelatedDemands(Set<MaterialBO> subordinateMaterials,
                                                                                           Map<MaterialBO, MaterialPlanInstanceBO> collectMpInstances) {
        if (CollectionUtils.isEmpty(subordinateMaterials)) {
            return Collections.emptyMap();
        }
        Multimap<LocalDate, PlanOrderBomRelationDemandBO> correlatedDemands = ArrayListMultimap.create();
        // 遍历子件物料
        for (MaterialBO subordinateMaterial : subordinateMaterials) {
            MaterialPlanInstanceBO subordinateMaterialPlan = collectMpInstances.get(subordinateMaterial);
            if (null != subordinateMaterialPlan) {
                for (Map.Entry<LocalDate, MaterialPlanDetailBO> entry : subordinateMaterialPlan.getMaterialPlanDetails().entrySet()) {
                    // 获取子件物料对应的相关需求指标-作为计划订单母件物料对应的相关需求(计划订单确认时同步供需存看板)
                    final BigDecimal correlatedDemand = entry.getValue().getIndicators().get(Indicators.CORRELATED.getIndicatorCode());
                    if (null != correlatedDemand && correlatedDemand.compareTo(BigDecimal.ZERO) > 0) {
                        PlanOrderBomRelationDemandBO demand = new PlanOrderBomRelationDemandBO();
                        demand.setAmount(correlatedDemand.intValue());
                        demand.setTenantId(subordinateMaterial.getTenantId());
                        demand.setMaterialCode(subordinateMaterial.getMaterialCode());
                        demand.setLogicalPlantNo(subordinateMaterial.getLogicalPlantNo());
                        correlatedDemands.put(entry.getKey(), demand);
                    }
                }
            }
        }
        return correlatedDemands.asMap();
    }

    /**
     * 根据物料整合物料计划
     * <p>
     * 基于当前版本,同个物料可能作为多个独立需求的子件存在(A-B-C/B-C)，会产出多份物料计划，在创建计划订单时要求以物料维度进行整合
     * </p>
     *
     * @param planInstance 计划实例
     * @return 整合后的物料计划实例
     */
    private Map<MaterialBO, MaterialPlanInstanceBO> collectMaterialPlanForSameMaterial(PlanInstanceBO planInstance) {
        Map<MaterialBO, MaterialPlanInstanceBO> assembleMpInstances = Maps.newHashMap();

        // 1. 根据物料编码进行分组
        Map<MaterialBO, List<MaterialPlanInstanceBO>> materialPlanGroup = planInstance.getMaterialPlanInstances()
                .stream().collect(Collectors.groupingBy(mpInstance -> MaterialBO.of(mpInstance.getMaterialCode(), mpInstance.getLogicalPlantNo(), mpInstance.getTenantId())));

        // 2. 遍历分组，整合物料计划指标信息
        materialPlanGroup.forEach((material, coll) -> {
            // 分组中有且仅有一条记录，说明无需整合
            if (CollectionUtils.size(coll) == 1) {
                assembleMpInstances.put(material, coll.get(0));
                return;
            }
            // 分组中存在多条记录时，copy首条记录用于进行数据整合
            final MaterialPlanInstanceBO prime = coll.get(0);
            MaterialPlanInstanceBO collect = prime.copyBasic();
            collect.setExtAttrs(new JSONObject());
            collect.setMaterialPlanDetails(Maps.newHashMap());
            // 深拷贝物料计划详情信息
            for (Map.Entry<LocalDate, MaterialPlanDetailBO> entry : prime.getMaterialPlanDetails().entrySet()) {
                collect.getMaterialPlanDetails().put(entry.getKey(), entry.getValue().copyBasic());
            }
            assembleMpInstances.put(material, collect);

            // 遍历分组中的物料计划，整合物料详情指标
            for (MaterialPlanInstanceBO mpInstance : coll) {
                collect.getExtAttrs().putAll(mpInstance.getExtAttrs());
                // 遍历物料计划详情
                mpInstance.getMaterialPlanDetails().forEach((date, detail) ->
                        // 物料计划详情合并指标数据
                        collect.getMaterialPlanDetails().merge(date, detail, (exist, v2) -> exist.mergeIndicators(v2.getIndicators())));
            }
        });

        return assembleMpInstances;
    }

    /**
     * 构建物料计划产出上下文
     *
     * @param material          物料计划
     * @param plan              计划方案
     * @param materialParameter 物料计划参数
     * @param geneType          产出类型
     * @return 物料计划产出上下文
     */
    private MaterialPlanGeneContext buildMaterialPlanContext(MaterialBO material,
                                                             PlanBO plan,
                                                             PlanMaterialParameterBO materialParameter,
                                                             PlanGeneType geneType) {
        final String materialCode = material.getMaterialCode();
        final String lpCode = material.getLogicalPlantNo();
        final String tenantId = material.getTenantId();

        MaterialPlanGeneContext context = new MaterialPlanGeneContext();
        context.setPlan(plan);
        context.setGeneType(geneType);
        context.setMaterial(material);
        context.setPlanMaterialParameter(materialParameter);
        BigDecimal initialStock = inventorySupportService.queryInventory(materialCode, lpCode, tenantId);
        context.setInitialStock(Optional.ofNullable(initialStock).orElse(BigDecimal.ZERO));
        return context;
    }

    private BomMaterialPlanGeneContext buildBomMaterialPlanContext(MaterialPlanGeneContext delegate,
                                                                   Long amount,
                                                                   MaterialPlanInstanceBO root,
                                                                   MaterialPlanInstanceBO parent) {
        BomMaterialPlanGeneContext context = new BomMaterialPlanGeneContext(delegate);
        context.setRoot(root);
        context.setAmount(amount);
        context.setParent(parent);
        return context;
    }

    /**
     * 获取操作人
     * <pre>
     *     1. 若计划类型=自动，操作人为计划创建人员
     *     2. 若计划类型=手动，操作人为计划运行人员，若计划人员为空，则取计划创建人员
     * </pre>
     *
     * @param plan    计划方案
     * @return 操作人
     */
    private String decideOperator(PlanBO plan) {
        String operator = plan.getCreatorId();
//        PlanTypeEnum planType = PlanTypeEnum.getByCode(plan.getPlanType());
//        if (planType == PlanTypeEnum.MANUAL && StringUtils.isNoneBlank(request.getUserId())) {
//            operator = request.getUserId();
//        }
        return operator;
    }

    /**
     * 构建物料计划扩展属性
     *
     * @param context 物料计划产出上下文
     * @return 扩展属性
     */
    private JSONObject buildMaterialPlanExtAttrs(MaterialPlanGeneContext context) {
        JSONObject extAttrs = new JSONObject();
        extAttrs.put(GENE_TYPE.getCode(), context.getGeneType().name());
        extAttrs.put(START_DATE.getCode(), context.planStartDate());
        extAttrs.put(INITIAL_STOCK.getCode(), context.getInitialStock());

        final PlanBO plan = context.getPlan();
        String planQuantityCode = MapUtils.getString(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.PLAN_QUANTITY_STRATEGY.getCode());
        PlanQuantityStrategyEnum planQuantityEnum = PlanQuantityStrategyEnum.getByCode(planQuantityCode);

        String indefiniteQuantityCode = MapUtils.getString(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.INDEFINITE_QUANTITY_STRATEGY.getCode());
        IndefiniteQuantityStrategyEnum indefiniteQuantityEnum = IndefiniteQuantityStrategyEnum.getByCode(indefiniteQuantityCode);

        String orderStrategyCode = MapUtils.getString(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ORDER_STRATEGY.getCode());
        OrderStrategyEnum orderStrategyEnum = OrderStrategyEnum.getByCode(orderStrategyCode);

        extAttrs.put(PRODUCE_TYPE.getCode(), PlanProduceEnum.of(orderStrategyEnum, planQuantityEnum, indefiniteQuantityEnum).getCode());

        PlanMaterialParameterBO parameter = context.getPlanMaterialParameter();
        extAttrs.put(DEMAND_STRATEGY.getCode(), parameter.getDemandStrategyCode());
        extAttrs.put(MATERIAL_PLAN_TYPE.getCode(), parameter.getPlanTypeCode());
        extAttrs.put(VENDOR_LEAD_TIME.getCode(), parameter.getVendorLeadTime());
        extAttrs.put(DEMAND_TIME_FENCE.getCode(), parameter.getDemandTimeFence());
        extAttrs.put(PLANNING_TIME_FENCE.getCode(), parameter.getPlanningTimeFence());
        extAttrs.put(ORDER_QUANTITY.getCode(), parameter.getOrderQuantity());
        extAttrs.put(ORDER_CYCLE_TIME.getCode(), parameter.getOrderCycleTime());
        extAttrs.put(SAFETY_STOCK.getCode(), parameter.getSafetyStock());
        extAttrs.put(LOSS_RATE.getCode(), parameter.getLossRate());
        extAttrs.put(FINISHED_RATE.getCode(), parameter.getFinishedRate());

        Integer enableLossRateCode = MapUtils.getInteger(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ENABLE_LOSS_RATE.getCode());
        extAttrs.put(ENABLE_LOSS_RATE.getCode(), EnableLossRateEnum.getByCode(enableLossRateCode) == EnableLossRateEnum.ON);
        Integer enableFinishedRateCode = MapUtils.getInteger(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ENABLE_FINISHED_RATE.getCode());
        extAttrs.put(ENABLE_FINISHED_RATE.getCode(), EnableFinishedRateEnum.getByCode(enableFinishedRateCode) == EnableFinishedRateEnum.ON);
        Integer enableSafetyStockCode = MapUtils.getInteger(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ENABLE_SAFETY_STOCK.getCode());
        extAttrs.put(ENABLE_SAFETY_STOCK.getCode(), EnableSafetyStockEnum.getByCode(enableSafetyStockCode) == EnableSafetyStockEnum.ON);
        extAttrs.put(ENABLE_BOM.getCode(), PlanRelatedBomEnum.enableBom(plan.getRelatedBom()));
        return extAttrs;
    }

    /**
     * 产出物料计划
     *
     * @param planInstance 计划实例
     * @param context      物料计划产出上下文
     * @return 物料计划实例
     */
    private MaterialPlanInstanceBO generateForMaterial(PlanInstanceBO planInstance, MaterialPlanGeneContext context) {
        final String materialCode = context.getMaterial().getMaterialCode();
        final String lpCode = context.getMaterial().getLogicalPlantNo();
        final String tenantId = context.getMaterial().getTenantId();

        // 1.构建物料计划实例模型
        InvPlantBO plant = inventorySupportService.findPlant(lpCode, tenantId);
        ProductBO product = productSupportService.findProduct(materialCode, tenantId);
        MaterialPlanInstanceBO materialPlanInstance = new MaterialPlanInstanceBO();
        materialPlanInstance.setInstanceId(planInstance.getId());
        materialPlanInstance.setMaterialCode(materialCode);
        materialPlanInstance.setMaterialDesc(Optional.ofNullable(product).map(ProductBO::getName).orElse(null));
        materialPlanInstance.setLogicalPlantNo(lpCode);
        materialPlanInstance.setLogicalPlantName(Optional.ofNullable(plant).map(InvPlantBO::getPlantName).orElse(null));
        materialPlanInstance.setDemandStrategyCode(context.getPlanMaterialParameter().getDemandStrategyCode());
        materialPlanInstance.setTenantId(tenantId);
        if (context instanceof BomMaterialPlanGeneContext) {
            BomMaterialPlanGeneContext bomContext = (BomMaterialPlanGeneContext) context;
            materialPlanInstance.setRootId(bomContext.getRoot().getId());
            materialPlanInstance.setParentId(bomContext.getParent().getId());
        }
        materialPlanInstance.setCreatorId(planInstance.getCreatorId());
        materialPlanInstance.setCreateUserName(planInstance.getCreateUserName());
        materialPlanInstance.setStatus(InstanceStatusEnum.PROCESSING.getCode());
        materialPlanInstance.setGeneType(context.getGeneType().getCode());
        materialPlanInstance.setExtAttrs(buildMaterialPlanExtAttrs(context));

        Map<LocalDate, MaterialPlanDetailBO> materialPlanDetails = Maps.newHashMap();

        // 2.遍历计划期，计算指标并构建物料计划详情
        try {
            // 设置线程上下文-避免指标重复计算
            FormulaContext.init();
            MaterialFactor factor = factorHelper.buildFactor(context);

            for (LocalDate planDate = context.planStartDate();
                 !planDate.isAfter(context.planEndDate());
                 planDate = planDate.plusDays(1)) {
                // 设置计划日期
                factor.setPlanDate(planDate);
                // 指标计算
                materialPlanDetails.put(planDate, buildMaterialDetail(materialPlanInstance, factor));
            }
            materialPlanInstance.setStatus(InstanceStatusEnum.SUCCESS.getCode());

            materialPlanInstance.setMaterialPlanDetails(materialPlanDetails);

            postProcessMaterialPlanInstance(planInstance, materialPlanInstance);

        } catch (Exception e) {
            log.error("calculate material plan error|context = {}", context, e);
            // 清空详情列表
            materialPlanDetails.clear();
            // 设置物料计划执行失败
            materialPlanInstance.setStatus(InstanceStatusEnum.FAIL.getCode());
        } finally {
            // 清空线程上下文
            FormulaContext.clear();
        }

        // 3.保存物料计划实例
        saveMaterialPlanInstance(materialPlanInstance);

        return materialPlanInstance;
    }

    /**
     * 后置处理物料计划实例
     *
     * @param planInstance         计划实例
     * @param materialPlanInstance 物料计划实例
     */
    private void postProcessMaterialPlanInstance(PlanInstanceBO planInstance,
                                                 MaterialPlanInstanceBO materialPlanInstance) {
        if (CollectionUtils.isNotEmpty(materialPlanInstancePostProcessors)) {
            for (MaterialPlanInstancePostProcessor processor : materialPlanInstancePostProcessors) {
                processor.postProcess(planInstance, materialPlanInstance);
            }
        }
    }

    /**
     * 构建物料计划详情
     *
     * @param materialPlanInstance 物料计划实例
     * @param factor               物料因子
     * @return 物料计划详情
     */
    private MaterialPlanDetailBO buildMaterialDetail(MaterialPlanInstanceBO materialPlanInstance,
                                                     MaterialFactor factor) {
        MaterialPlanDetailBO detail = new MaterialPlanDetailBO();
        detail.setMaterialCode(materialPlanInstance.getMaterialCode());
        detail.setMaterialDesc(materialPlanInstance.getMaterialDesc());
        detail.setLogicalPlantNo(materialPlanInstance.getLogicalPlantNo());
        detail.setPlanDate(DateUtils.toDate(factor.getPlanDate()));
        detail.setTenantId(materialPlanInstance.getTenantId());
        detail.setCreatorId(materialPlanInstance.getCreatorId());

        // 后期可以维护为根据租户配置获取对应的指标值集合
        boolean isIndependentReport = materialPlanInstance.getParentId() == null;
        withIndicators(detail, factor, isIndependentReport);

        postProcessMaterialPlanDetail(materialPlanInstance, detail);

        return detail;
    }

    /**
     * 指标计算
     *
     * @param materialPlanDetail 物料计划详情
     * @param factor             物料因子
     * @param isIndependent      是否独立需求
     */
    private void withIndicators(MaterialPlanDetailBO materialPlanDetail, MaterialFactor factor, boolean isIndependent) {
        // 获取独立需求&相关需求对应的指标集合
        Indicators[] indicators = isIndependent ? Indicators.ofDefaultReport() : Indicators.ofCorrelatedReport();
        for (Indicators indicatorSpec : indicators) {
            Formula formula = Formulas.formula(indicatorSpec);
            Indicator indicator = formula.apply(factor);
            materialPlanDetail.appendIndicator(indicatorSpec.getIndicatorCode(), indicator.value());
            materialPlanDetail.appendIndicatorExtAttrs(indicatorSpec.getIndicatorCode(), indicator.extAttrs());
        }
    }

    /**
     * 后置处理物料计划详情
     *
     * @param materialPlanInstance 物料计划实例
     * @param detail               物料计划详情
     */
    private void postProcessMaterialPlanDetail(MaterialPlanInstanceBO materialPlanInstance, MaterialPlanDetailBO detail) {
        if (CollectionUtils.isNotEmpty(materialPlanDetailPostProcessors)) {
            for (MaterialPlanDetailPostProcessor processor : materialPlanDetailPostProcessors) {
                processor.postProcess(materialPlanInstance, detail);
            }
        }
    }

    @Override
    public PagedSingleResponse<MaterialPlanInstanceWithPlanBO> pageQueryMaterialPlanInstances(MaterialPlanInstanceCondition condition) {
        BaseAssert.notNull(condition, "material instance retrieve condition cannot be null");

        // 总条目数统计
        long count = materialPlanInstanceDao.count(condition);
        if (count == 0) {
            return ofPage(condition, 0, Lists.newArrayList());
        }

        // 分页查询
        List<MaterialPlanInstanceWithPlanPO> planMaterialPlanInstances = materialPlanInstanceDao.selectByCondition(condition);

        // 结果封装
        return ofPage(condition, count, planMaterialPlanInstances.stream()
                .map(planGenerateConverter::convertMaterialPlanInstance)
                .collect(Collectors.toList()));
    }

    private static <T extends Serializable> PagedSingleResponse<T> ofPage(PageCondition condition, long count, List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return PagedSingleResponse.success(condition.getPageNum(), condition.getSize(), 0, Lists.newArrayList());
        }
        return PagedSingleResponse.success(condition.getPageNum(), condition.getSize(), count, data);
    }

    @Override
    public MaterialPlanReportBO queryMaterialPlanReportByMaterialInstance(Long materialInstanceId,
                                                                          String tenantId, CollectType collectType) {

        MaterialPlanInstancePO materialPlanInstance = materialPlanInstanceDao.selectById(materialInstanceId, tenantId);
        BaseAssert.notNull(materialPlanInstance, PI_REPORT_NOT_EXIST.getDesc());

        PlanInstancePO planInstance = planInstanceDao.selectById(materialPlanInstance.getInstanceId());

        List<MaterialPlanDetailPO> materialPlanDetails = materialPlanDetailDao.selectByInstanceId(materialInstanceId);

        return assemble(planInstance, materialPlanInstance, materialPlanDetails, collectType);
    }

    private MaterialPlanReportBO assemble(
            PlanInstancePO planInstance,
            MaterialPlanInstancePO materialPlanInstance,
            List<MaterialPlanDetailPO> materialPlanDetails,
            CollectType collectType) {
        MaterialPlanReportBO materialPlanReport = new MaterialPlanReportBO();
        materialPlanReport.setTitle(assembleTitle(planInstance, materialPlanInstance));
        materialPlanReport.setParam(assembleParam(materialPlanInstance));
        materialPlanReport.setPeriod(assemblePeriod(materialPlanInstance, materialPlanDetails, collectType));
        materialPlanReport.setInitIndicator(assembleInitIndicator(materialPlanInstance));
        materialPlanReport.setIndicators(assembleIndicators(materialPlanInstance, materialPlanDetails, collectType));
        return materialPlanReport;
    }

    private MaterialPlanReportTitleBO assembleTitle(PlanInstancePO planInstance, MaterialPlanInstancePO materialPlanInstance) {
        MaterialPlanReportTitleBO title = new MaterialPlanReportTitleBO();
        title.setMaterialCode(materialPlanInstance.getMaterialCode());
        title.setMaterialDesc(materialPlanInstance.getMaterialDesc());
        title.setPlanVersion(planInstance.getPlanVersion());
        title.setCreateDate(DateUtils.toLocalDate(planInstance.getCalStartTime()));
        title.setOperator(planInstance.getCreatorId());
        try {
            title.setOperator(planParticipantStub.getUserName(planInstance.getCreatorId()));
        } catch (Exception e) {
            log.error("query operator name error|user id={}", planInstance.getCreatorId(), e);
        }
        return title;
    }


    private PlanMaterialParameterBO findMaterialParameter(MaterialBO material) {
        PlanMaterialParamQueryReqBO request = new PlanMaterialParamQueryReqBO();
        request.setTenantId(material.getTenantId());
        request.setMaterialCode(material.getMaterialCode());
        request.setLogicalPlantNo(material.getLogicalPlantNo());
        return planConfigService.queryByMaterialCodeAndLogicalPlantNo(request);
    }

    private MaterialPlanReportParamBO assembleParam(MaterialPlanInstancePO materialPlanInstance) {
        MaterialPlanReportParamBO param = new MaterialPlanReportParamBO();

        JSONObject extAttrs = JSON.parseObject(materialPlanInstance.getExtAttrs());
        // 从计划实例中获取期初库存
        param.setCurrentStock(extAttrs.getBigDecimal(INITIAL_STOCK.getCode()));
        // 从计划实例中获取产出类型
        param.setProduceType(extAttrs.getInteger(PRODUCE_TYPE.getCode()));

        // 从计划实例中获取报表参数
        param.setVendorLeadTime(Optional.ofNullable(extAttrs.getInteger(VENDOR_LEAD_TIME.getCode())).orElse(0));
        param.setSafetyStock(Optional.ofNullable(extAttrs.getLong(SAFETY_STOCK.getCode())).orElse(0L));
        param.setOrderQuantity(Optional.ofNullable(extAttrs.getLong(ORDER_QUANTITY.getCode())).orElse(0L));
        param.setDemandTimeFence(Optional.ofNullable(extAttrs.getInteger(DEMAND_TIME_FENCE.getCode())).orElse(0));
        param.setPlanningTimeFence(Optional.ofNullable(extAttrs.getInteger(PLANNING_TIME_FENCE.getCode())).orElse(0));
        return param;
    }

    private MaterialPlanReportPeriodBO assemblePeriod(MaterialPlanInstancePO materialPlanInstance,
                                                      List<MaterialPlanDetailPO> materialPlanDetails,
                                                      CollectType collectType) {
        MaterialPlanReportPeriodBO period = new MaterialPlanReportPeriodBO();
        // 按计划日期排序
        materialPlanDetails.sort(Comparator.comparing(MaterialPlanDetailPO::getPlanDate));

        // 获取计划开始日期
        final LocalDate prime = findPrimeDate(materialPlanInstance, materialPlanDetails);

        // 1.按CollectRange分组排序，使用TreeMap保证分组排序
        // 2.分组后获取CollectRange#lowEndPoint作为时段返回
        period.setPeriodStartDates(materialPlanDetails.stream()
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(detail -> collectType.range(DateUtils.toLocalDate(detail.getPlanDate()), prime), Maps::newTreeMap, Collectors.toList())
                        , detailGroup -> detailGroup.keySet().stream().map(range -> range.getRange().lowerEndpoint()).collect(Collectors.toList()))));
        return period;
    }

    private LocalDate findPrimeDate(MaterialPlanInstancePO materialPlanInstance,
                                    List<MaterialPlanDetailPO> orderedDetails) {
        LocalDate prime = null;
        if (StringUtils.isNoneBlank(materialPlanInstance.getExtAttrs())) {
            JSONObject extAttrs = JSON.parseObject(materialPlanInstance.getExtAttrs());
            prime = extAttrs.getObject(START_DATE.getCode(), LocalDate.class);
        }
        if (null == prime) {
            prime = DateUtils.toLocalDate(orderedDetails.get(0).getPlanDate());
        }
        return prime;
    }

    private MaterialPlanReportInitIndicatorBO assembleInitIndicator(MaterialPlanInstancePO materialPlanInstance) {
        MaterialPlanReportInitIndicatorBO initIndicator = new MaterialPlanReportInitIndicatorBO();

        JSONObject extAttrs = JSON.parseObject(materialPlanInstance.getExtAttrs());
        BigDecimal initialPab = Optional.ofNullable(extAttrs.getBigDecimal(INITIAL_PAB.getCode())).orElse(BigDecimal.ZERO);
        BigDecimal initialPlanInvest = Optional.ofNullable(extAttrs.getBigDecimal(INITIAL_PLAN_INVEST.getCode())).orElse(BigDecimal.ZERO);
        initIndicator.putInitIndicator(Indicators.PAB.getIndicatorCode(), initialPab);
        initIndicator.putInitIndicator(Indicators.PLAN_INVEST.getIndicatorCode(), initialPlanInvest);

        return initIndicator;
    }

    private List<MaterialPlanReportIndicatorBO> assembleIndicators(MaterialPlanInstancePO materialPlanInstance,
                                                                   List<MaterialPlanDetailPO> materialPlanDetails,
                                                                   CollectType collectType) {
        // 按计划日期排序
        materialPlanDetails.sort(Comparator.comparing(MaterialPlanDetailPO::getPlanDate));

        // 获取计划开始日期
        final LocalDate prime = findPrimeDate(materialPlanInstance, materialPlanDetails);

        // 1.按CollectRange分组排序，使用TreeMap保证分组排序
        Map<CollectRange, List<MaterialPlanDetailPO>> group = Maps.newTreeMap();
        for (MaterialPlanDetailPO detail : materialPlanDetails) {

            CollectRange range = collectType.range(DateUtils.toLocalDate(detail.getPlanDate()), prime);

            List<MaterialPlanDetailPO> details = group.getOrDefault(range, Lists.newArrayList());
            details.add(detail);
            group.put(range, details);
        }

        boolean isIndependent = materialPlanInstance.getParentId() == null;
        Indicators[] reportIndicators = isIndependent ? Indicators.ofDefaultReport() : Indicators.ofCorrelatedReport();
        // 指标统计
        Map<String, MaterialPlanReportIndicatorBO> indicators = Maps.newHashMap();

        // 2.对应分组指标汇总、并进行矩阵转置
        group.forEach((range, details) -> {
            Map<String, BigDecimal> indicatorValues = Maps.newHashMap();
            for (MaterialPlanDetailPO materialDetail : details) {
                JSONObject jsonObject = JSONObject.parseObject(materialDetail.getPlanDetail());
                JSONObject indicatorJson = jsonObject.getJSONObject(MaterialPlanDetailNsExtKeyEnum.INDICATORS.getCode());

                for (Indicators spec : reportIndicators) {
                    MaterialPlanReportIndicatorBO indicator = indicators.get(spec.getIndicatorCode());
                    if (null == indicator) {
                        indicator = new MaterialPlanReportIndicatorBO();
                        indicator.setIndicatorCode(spec.getIndicatorCode());
                        indicator.setIndicatorName(spec.getIndicatorName());
                        indicators.put(spec.getIndicatorCode(), indicator);
                    }
                    BigDecimal gatherIndicator = indicatorValues.getOrDefault(spec.getIndicatorCode(), BigDecimal.ZERO);
                    BigDecimal dateIndicator = Optional.ofNullable(indicatorJson.getBigDecimal(spec.getIndicatorCode())).orElse(BigDecimal.ZERO);
                    indicatorValues.put(spec.getIndicatorCode(), gatherIndicator.add(dateIndicator));
                }
            }
            for (Map.Entry<String, BigDecimal> value : indicatorValues.entrySet()) {
                MaterialPlanReportIndicatorBO indicator = indicators.get(value.getKey());
                indicator.appendIndicator(value.getValue());
            }
        });

        // 排序输出
        List<MaterialPlanReportIndicatorBO> sortIndicators = Lists.newArrayList();
        Arrays.stream(reportIndicators).forEach(indicator -> {
            if (indicators.containsKey(indicator.getIndicatorCode())) {
                sortIndicators.add(indicators.get(indicator.getIndicatorCode()));
            }
        });

        return sortIndicators;
    }

    @Override
    public MaterialPlanInstanceBomNodeBO renderMaterialBomTree(Long materialInstanceId, String tenantId) {
        // 由materialInstanceId查询对应根节点记录
        MaterialPlanInstancePO materialPlanInstance = materialPlanInstanceDao.selectRootByAccordingId(materialInstanceId, tenantId);
        // 根节点物料计划实例为空
        if (null == materialPlanInstance) {
            return null;
        }
        // PO转BO
        MaterialPlanInstanceBO rootPlan = planGenerateConverter.convertMaterialPlanInstance(materialPlanInstance);
        if (null == rootPlan.getExtAttrs() || !rootPlan.getExtAttrs().containsKey(BOM_TREE.getCode())) {
            return null;
        }
        return rootPlan.getExtAttrs().getObject(BOM_TREE.getCode(), MaterialPlanInstanceBomNodeBO.class);
    }
}
