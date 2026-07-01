package com.inventory.middle.application.plan.order.service.impl;

import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.enums.DemandStrategyEnum;
import com.inventory.middle.domain.plan.common.enums.MaterialSceneTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanCoverMaterialTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.domain.plan.common.enums.SafetyStockOptionEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.domain.plan.common.ex.SpmException;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.config.bo.*;
import com.inventory.middle.application.plan.config.convertor.PlanConfigConverter;
import com.inventory.middle.application.plan.config.enums.*;
import com.inventory.middle.application.plan.config.rule.chain.*;
import com.inventory.middle.application.plan.config.service.PlanConfigService;
import com.inventory.middle.infra.plan.persistence.dao.DemandPlanDao;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.*;
import com.inventory.middle.infra.plan.persistence.entity.*;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.PagedSingleResponse;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划子域计划配置服务实现
 * @date 2021/9/27 16:05
 */
@Service
@Slf4j
public class PlanConfigServiceImpl implements PlanConfigService {

    @Resource
    private PlanConfigDao planConfigDao;

    @Resource
    private DemandPlanDao demandPlanDao;

    @Resource
    private BatchCreatePlanMaterialValidatorChain batchCreatePlanMaterialValidatorChain;

    @Resource
    private BatchCreatePlanMaterialParamValidatorChain batchCreatePlanMaterialParamValidatorChain;

    @Resource
    private UpdatePlanMaterialParamValidatorChain updatePlanMaterialParamValidatorChain;

    @Resource
    private CreatePlanValidatorChain createPlanValidatorChain;

    @Resource
    private UpdatePlanValidatorChain updatePlanValidatorChain;

    @Resource
    private ChangePlanStatusValidatorChain changePlanStatusValidatorChain;

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;


    @Override
    public PlanMaterialParamBatchCreateResBO batchCreatePlanMaterialParam(PlanMaterialParamBatchCreateReqBO bo) {
        // 校验数据
        ValidateMessage resultMessage = batchCreatePlanMaterialParamValidatorChain.doProcess(ValidateMessage.builder().t(bo).build());
        PlanMaterialParamBatchCreateResBO resBO = (PlanMaterialParamBatchCreateResBO) resultMessage.getE();
        if (!CollectionUtils.isEmpty(resBO.getFailedDetails())) {
            return resBO;
        }
        List<PlanMaterialParameterPO> pos = bo.getPlanMaterialParamList().stream()
                .map(d -> PlanConfigConverter.convertCreatePlanMaterialParameterBO2PO(d))
                .collect(Collectors.toList());
        planConfigDao.batchSavePlanMaterialParam(pos);
        // 3.返回结果
        return resBO;
    }

    @Override
    public Boolean updatePlanMaterialParam(PlanMaterialParameterBO bo) {
        // 校验数据
        updatePlanMaterialParamValidatorChain.doProcess(ValidateMessage.builder().t(bo).build());
        int result = planConfigDao.updatePlanMaterialParam(PlanConfigConverter.convertUpdatePlanMaterialParameterBO2PO(bo));
        return result > 0;
    }

    @Override
    public PlanMaterialParameterBO queryByMaterialCodeAndLogicalPlantNo(PlanMaterialParamQueryReqBO bo) {
        PlanMaterialParamQueryCondition condition = PlanConfigConverter.convertPlanMaterialParamQueryReqBO2Condition(bo);
        PlanMaterialParameterPO po = planConfigDao.selectByMaterialCodeAndLogicalPlantNo(condition);
        return PlanConfigConverter.convertPlanMaterialParameterPO2BO(po);
    }

    @Override
    public PagedSingleResponse<PlanMaterialParameterBO> pageQueryPlanMaterialParam(PlanMaterialParamPageReqBO bo) {
        // 转换condition
        PlanMaterialParamPageCondition condition = PlanConfigConverter.convertPlanMaterialParamPageReqBO2Condition(bo);
        // 查库
        PageInfo<PlanMaterialParameterPO> pageInfo = planConfigDao.pageQueryPlanMaterialParam(condition);
        // 转换BO
        List<PlanMaterialParameterBO> data = pageInfo.getList().stream()
                .map(d -> PlanConfigConverter.convertPlanMaterialParameterPO2BO(d))
                .collect(Collectors.toList());
        // 包装PagedSingleResponse
        return PagedSingleResponse.success(bo.getPageNum(), bo.getSize(), pageInfo.getTotal(), data);
    }

    @Override
    public Boolean createPlan(PlanBO bo) {
        // 校验数据
        createPlanValidatorChain.doProcess(ValidateMessage.builder().t(bo).build());
        // 计划方案号 是否要用sequence
        String nowTime = DateUtils.dateToString(new Date(), DateUtils.yyyyMMddHHmmss);
        String planCode = CommonConstants.PLAN_CODE.replace(CommonConstants.PLACEHOLDER_$, nowTime);
        PlanPO po = PlanConfigConverter.convertCreatePlanBO2PO(bo, planCode);
        // 落库
        int result = planConfigDao.savePlan(po);
        return result > 0;
    }

    @Override
    public Boolean updatePlan(PlanBO bo) {
        // 校验数据
        updatePlanValidatorChain.doProcess(ValidateMessage.builder().t(bo).build());
        PlanPO po = PlanConfigConverter.convertUpdatePlanBO2PO(bo);
        List<String> currentLogicalPlants = bo.getCoverLogicalPlantNos();
        int result = planConfigDao.updatePlan(po, currentLogicalPlants);
        return result > 0;
    }

    @Override
    public PlanBO queryPlanById(Long id, String tenantId) {
        log.info("enter PlanConfigServiceImpl queryPlanById()");
        PlanPO po = planConfigDao.queryPlanById(id);
        if (Objects.isNull(po)) {
            return null;
        }
        if (!po.getTenantId().equals(tenantId)) {
            throw new SpmException(ResponseCodeEnum.PLAN_NO_AUTH.getCode(), ResponseCodeEnum.PLAN_NO_AUTH.getDesc());
        }
        PlanBO bo = PlanConfigConverter.convertPlanPO2BO(po);
        // 封装需求文件
        assembleDemandPlanFile(bo);
        // 封装逻辑仓信息
        assembleLogicalPlant(bo);
        log.info("exit PlanConfigServiceImpl queryPlanById()");
        return bo;
    }

    /**
     * 封装计划方案逻辑仓信息
     *
     * @param bo
     */
    private void assembleLogicalPlant(PlanBO bo) {
        log.info("enter PlanConfigServiceImpl assembleLogicalPlant()");
        // 根据租户Id查询逻辑仓
        List<InvPlantBO> plants = logicalPlantQueryService.list(bo.getTenantId());
        // 过滤掉未覆盖的逻辑仓信息
        plants = plants.stream().filter(d -> bo.getCoverLogicalPlantNos().contains(d.getPlantCode())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(plants)) {
            Map<String, String> logicalPlantMap = plants.stream().collect(Collectors.toMap(InvPlantBO::getPlantCode, InvPlantBO::getPlantName, (key1, key2) -> key2));
            bo.setCoverLogicalPlant(logicalPlantMap);
        }
        log.info("exit PlanConfigServiceImpl assembleLogicalPlant()");
    }

    /**
     * 封装计划方案需求文件信息
     *
     * @param bo
     */
    private void assembleDemandPlanFile(PlanBO bo) {
        log.info("enter PlanConfigServiceImpl assembleDemandPlanFile()");
        // 查询需求文件
        List<DemandPlanPO> demandPlans = demandPlanDao.selectByLogicalPlantNos(bo.getCoverLogicalPlantNos(), bo.getTenantId());
        if (!CollectionUtils.isEmpty(demandPlans)) {
            Map<Long, String> demandPlanMap = demandPlans.stream().collect(Collectors.toMap(DemandPlanPO::getId, DemandPlanPO::getPlanVersion, (key1, key2) -> key2));
            bo.setDemandPlanFile(demandPlanMap);
        }
        log.info("exit PlanConfigServiceImpl assembleDemandPlanFile()");
    }

    @Override
    public List<PlanBO> queryPlanByType(PlanQueryByTypeReqBO bo) {
        PlanQueryByTypeCondition condition = PlanConfigConverter.convertPlanQueryByTypeReqBO2Condition(bo);
        List<PlanPO> pos = planConfigDao.queryPlanByType(condition);
        if (CollectionUtils.isEmpty(pos)) {
            return null;
        }
        return pos.stream().map(d -> PlanConfigConverter.convertPlanPO2BO(d)).collect(Collectors.toList());
    }

    @Override
    public PagedSingleResponse<PlanBO> pageQueryPlan(PlanPageReqBO bo) {
        // 转换Condition
        PlanPageCondition condition = PlanConfigConverter.convertPlanPageReqBO2Condition(bo);
        // 查库
        PageInfo<PlanPO> pageInfo = planConfigDao.pageQueryPlan(condition);

        // 查找指定物料类型的计划是否上传了物料清单
        List<PlanMaterialPO> planMaterials = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            List<Long> planIds = pageInfo.getList().stream().filter(d -> d.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.APPOINT.getCode()))
                    .map(PlanPO::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(planIds)) {
                planMaterials = planConfigDao.queryPlanMaterialByPlanIds(planIds);
            }
        }

        // 转换BO
        List<PlanBO> data = Lists.newArrayList();
        for (PlanPO po : pageInfo.getList()) {
            PlanBO planBO = PlanConfigConverter.convertPlanPO2BO(po);
            if (planBO.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.APPOINT.getCode())) {
                if (!CollectionUtils.isEmpty(planMaterials)) {
                    List<PlanMaterialPO> list = planMaterials.stream().filter(d -> d.getSourceId().equals(planBO.getId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(list)) {
                        planBO.setExported(1);
                    }
                }
            }
            data.add(planBO);
        }

        // 包装PagedSingleResponse
        return PagedSingleResponse.success(bo.getPageNum(), bo.getSize(), pageInfo.getTotal(), data);
    }

    @Override
    public PlanMaterialBatchCreateResBO batchCreatePlanMaterial(PlanMaterialBatchCreateReqBO bo) throws Exception {
        // 校验数据
        ValidateMessage resultMessage = batchCreatePlanMaterialValidatorChain.doProcess(ValidateMessage.builder().t(bo).build());
        PlanMaterialBatchCreateResBO resBO = (PlanMaterialBatchCreateResBO) resultMessage.getE();
        if (!CollectionUtils.isEmpty(resBO.getFailedDetails())) {
            return resBO;
        }
        List<PlanMaterialPO> planMaterials = bo.getPlanMaterialList().stream().map(d -> PlanConfigConverter.convertPlanMaterialBO2PO(d)).collect(Collectors.toList());
        planConfigDao.batchSavePlanMaterial(planMaterials);

        return resBO;
    }

    @Override
    public PlanMaterialBatchDeleteResBO batchDeletePlanMaterial(PlanMaterialBatchDeleteReqBO bo) {
        // 需要停用
        PlanBO planBO = queryPlanById(bo.getPlanId(), bo.getTenantId());

        if (planBO.getStatus().equals(PlanStatusEnum.ON.getCode())) {
            throw Ex.of(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_PLAN_STATUS_IS_ON);
        }
        // 物料清单为空 先上传
        List<PlanMaterialBO> existList = queryPlanMaterialByPlanId(bo.getPlanId(), bo.getTenantId());
        if (CollectionUtils.isEmpty(existList)) {
            throw Ex.of(ResponseCodeEnum.P_CHANGE_PLAN_STATUS_FAIL_PLAN_MATERIAL_IS_NULL);
        }

        // 不存在的物料不允许删除 方法命名有问题
        List<PlanMaterialBatchDeleteDetailBO> failedList = checkFaildList(existList, bo.getPlanMaterialList());

        if (CollectionUtils.isEmpty(failedList)) {
            List<PlanMaterialPO> poList = bo.getPlanMaterialList().stream().map(d -> PlanConfigConverter.convertPlanMaterialBO2PO(d)).collect(Collectors.toList());
            planConfigDao.batchDeletePlanMaterial(poList);
        }
        // 结果封装
        PlanMaterialBatchDeleteResBO resBO = new PlanMaterialBatchDeleteResBO();
        resBO.setTotalCount(bo.getPlanMaterialList().size());
        resBO.setFailedCount(failedList.size());
        resBO.setFailedDetails(failedList);
        return resBO;
    }

    private List<PlanMaterialBatchDeleteDetailBO> checkFaildList(List<PlanMaterialBO> existList, List<PlanMaterialBO> reqList) {
        List<PlanMaterialBatchDeleteDetailBO> failedList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(reqList)) {
            return failedList;
        }
        // 转换成map最后去匹配 key： materialCode + logicalPlantNo | value : planId
        Map<String, Long> inExecMaterialMap = existList.stream().collect(Collectors.toMap(k -> k.getMaterialCode() + k.getLogicalPlantNo(), PlanMaterialBO::getSourceId, (key1, key2) -> key2));
        Iterator<PlanMaterialBO> iterator = reqList.iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            String key = planMaterialBO.getMaterialCode() + planMaterialBO.getLogicalPlantNo();
            if (Objects.isNull(inExecMaterialMap.get(key))) {
                PlanMaterialBatchDeleteDetailBO failedBO = PlanConfigConverter.convertPlanMaterialBO2DeleteDetail(planMaterialBO, ResponseCodeEnum.P_DELETE_PLAN_MATERIAL_FAIL_NOT_EXIST.getDesc());
                failedList.add(failedBO);
            }
        }
        return failedList;
    }

    @Override
    public List<PlanMaterialBO> queryPlanMaterialByPlanId(Long planId, String tenantId) {
        List<PlanMaterialPO> pos = planConfigDao.queryPlanMaterialByPlanId(planId, tenantId, MaterialSceneTypeEnum.PLAN_MATERIAL_TYPE.getCode());
        if (CollectionUtils.isEmpty(pos)) {
            return null;
        }
        return pos.stream().map(d -> PlanConfigConverter.convertPlanMaterialPO2BO(d)).collect(Collectors.toList());
    }

    @Override
    public List<MaterialBO> queryMaterialsByPlan(PlanBO plan) {
        if (null == plan) {
            return Lists.newArrayList();
        }
        // 查询逻辑仓下所有物料信息
        PlanCoverMaterialTypeEnum coverType = PlanCoverMaterialTypeEnum.getByCode(plan.getCoverMaterialType());
        if (coverType == PlanCoverMaterialTypeEnum.ALL) {
            List<MaterialBO> materials = Lists.newArrayList();
            // 逻辑仓下所有物料
            Map<String, List<String>> materialsGroup =
                    inventoryMaterialApplicationService.listByLogicalPlantNos(
                            plan.getCoverLogicalPlantNos(), plan.getTenantId());
            for (Map.Entry<String, List<String>> materialGroup : materialsGroup.entrySet()) {
                for (String materialCode : materialGroup.getValue()) {
                    MaterialBO material = new MaterialBO();
                    material.setMaterialCode(materialCode);
                    material.setLogicalPlantNo(materialGroup.getKey());
                    material.setTenantId(plan.getTenantId());
                    materials.add(material);
                }
            }
            return materials;
        } else {
            List<PlanMaterialBO> materials = queryPlanMaterialByPlanId(plan.getId(), plan.getTenantId());
            if (CollectionUtils.isEmpty(materials)) {
                return Lists.newArrayList();
            }
            // 逻辑仓下指定物料
            return materials.stream().map(planMaterial -> {
                MaterialBO material = new MaterialBO();
                material.setTenantId(planMaterial.getTenantId());
                material.setMaterialCode(planMaterial.getMaterialCode());
                material.setLogicalPlantNo(planMaterial.getLogicalPlantNo());
                return material;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public PlanBO queryPlanByMaterial(MaterialBO material) {
        final String materialCode = material.getMaterialCode();
        final String logicalPlantNo = material.getLogicalPlantNo();
        final String tenantId = material.getTenantId();

        PlanBO planToUse = null;

        // 1. 根据物料覆盖类型=0(所有物料) & 逻辑仓查询计划
        PlanQueryByCoverMaterialTypeCondition typeCondition =
                PlanQueryByCoverMaterialTypeCondition.builder()
                        .tenantId(tenantId)
                        .coverMaterialType(PlanCoverMaterialTypeEnum.ALL.getCode())
                        .build();
        List<PlanPO> candidatePlans = planConfigDao.queryPlanByCoverMaterialType(typeCondition);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(candidatePlans)) {
            planToUse = candidatePlans.stream()
                    .filter(plan -> Splitter.on(",").splitToList(plan.getCoverLogicalPlant()).contains(logicalPlantNo))
                    .findFirst()
                    .map(PlanConfigConverter::convertPlanPO2BO)
                    .orElse(null);
        }
        if (null == planToUse) {
            // 2. 根据计划方案范围查询物料信息
            PlanMaterialQueryCondition condition = new PlanMaterialQueryCondition();
            condition.setMaterialCode(materialCode);
            condition.setLogicalPlantNo(logicalPlantNo);

            List<PlanMaterialPO> planMaterials = planConfigDao.queryPlanMaterialByMaterialCodeAndLogicalNo(Lists.newArrayList(condition));

            if (CollectionUtils.isNotEmpty(planMaterials)) {
                planToUse = queryPlanById(planMaterials.get(0).getSourceId(), planMaterials.get(0).getTenantId());
            }
        }
        return planToUse;
    }

    @Override
    public PlanParamEnumResBO getPlanParamEnumList() {
        PlanParamEnumResBO planParamEnums = new PlanParamEnumResBO();
        // 计划执行方案-订货规则
        Arrays.stream(PlanExecuteTypeEnum.values()).forEach(type -> type.rules().forEach(rule ->
                planParamEnums.appendTypeForRule(
                        PlanParamBO.of(String.valueOf(type.getCode()), type.getDesc()),
                        PlanParamBO.of(rule.getCode(), rule.getDesc()))));
        // 订货规则-产出规则
        Arrays.stream(OrderCalRuleEnum.values()).forEach(rule -> rule.produceTypes().forEach(produce ->
                planParamEnums.appendRuleForProduce(
                        PlanParamBO.of(rule.getCode(), rule.getDesc()),
                        PlanParamBO.of(produce.getCode(), produce.getDesc())
                )));

        return planParamEnums;
    }

    @Override
    public Boolean changeStatus(ChangeStatusPlanBO bo) {
        log.info("enter PlanConfigServiceImpl changeStatus");
        // 1.查出计划方案
        PlanBO planBO = queryPlanById(bo.getId(), bo.getTenantId());
        if (Objects.isNull(planBO)) {
            throw new SpmException(ResponseCodeEnum.PLAN_DATA_IS_NULL.getCode(), ResponseCodeEnum.PLAN_DATA_IS_NULL.getDesc());
        }
        if (!planBO.getTenantId().equals(bo.getTenantId())) {
            throw new SpmException(ResponseCodeEnum.PLAN_NO_AUTH.getCode(), ResponseCodeEnum.PLAN_NO_AUTH.getDesc());
        }
        // 2.计划状态变为生效时，需作校验链检查
        if (PlanStatusEnum.ON.getCode().equals(bo.getStatus())) {
            changePlanStatusValidatorChain.doProcess(ValidateMessage.builder().t(planBO).build());
        }
        // 3.状态修改
        ChangeStatusPlanPO po = PlanConfigConverter.convertChangePlanStatusBO2PO(bo);
        int result = planConfigDao.changeStatus(po);
        log.info("exit PlanConfigServiceImpl changeStatus");
        return result > 0;

    }

    @Override
    public PlanMaterialParamEnumResBO getPlanMaterialParamEnumList() {
        PlanMaterialParamEnumResBO result = new PlanMaterialParamEnumResBO();
        // 设置物料计划类型
        result.setPlanMaterialParamPlanTypes(Arrays.stream(PlanMaterialParamPlanTypeEnum.values())
                .map(planType -> PlanParamBO.of(String.valueOf(planType.getCode()), planType.getDesc()))
                .collect(Collectors.toList()));
        // 设置需求策略枚举
        result.setDemandStrategies(Arrays.stream(DemandStrategyEnum.values())
                .map(strategy -> PlanParamBO.of(strategy.getCode(), strategy.getDesc()))
                .collect(Collectors.toList()));
        // 设置物料需求类型
        result.setDemandTypes(Arrays.stream(MaterialParameterDemandTypeEnum.values())
                .map(option -> PlanParamBO.of(String.valueOf(option.getCode()), option.getDesc()))
                .collect(Collectors.toList()));

        // 设置安全库存计算方式
        result.setSafetyStockCalculateTypes(Arrays.stream(SafetyStockOptionEnum.values())
                .map(option -> PlanParamBO.of(String.valueOf(option.getCode()), option.getDesc()))
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public PlanTransferLogicalPlantsResBO queryPlanTransferLogicalPlants(QueryPlanTransferLogicalPlantsReqBO reqBO) {
        PlanTransferLogicalPlantsResBO resBO = new PlanTransferLogicalPlantsResBO();
        // 根据物料编码查询是否存在计划参数
        PlanQueryPlanTransferLogicalPlantsCondition condition = PlanConfigConverter.convertPlanQueryPlanTransferLogicalPlantsBO2Condition(reqBO);
        List<String> logicalPlantsByMaterial = planConfigDao.queryLogicalPlantsByMaterial(condition);
        if (CollectionUtils.isEmpty(logicalPlantsByMaterial)) {
            return resBO;
        }
        // 根据租户id获取逻辑仓信息
        List<InvPlantBO> plants = logicalPlantQueryService.list(reqBO.getTenantId());
        if (CollectionUtils.isEmpty(plants)) {
            return resBO;
        }
        List<LogicalPlantsResBO> list = new ArrayList<>();
        plants.forEach(logicalPlant -> {
            if (logicalPlantsByMaterial.contains(logicalPlant.getPlantCode())) {
                LogicalPlantsResBO bo = new LogicalPlantsResBO();
                bo.setLogicalPlantName(logicalPlant.getPlantName());
                bo.setLogicalPlantNo(logicalPlant.getPlantCode());
                list.add(bo);
            }
        });
        resBO.setTransferLogicalPlants(list);
        return resBO;
    }

}