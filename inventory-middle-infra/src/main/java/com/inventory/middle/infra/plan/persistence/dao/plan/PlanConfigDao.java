package com.inventory.middle.infra.plan.persistence.dao.plan;

import com.inventory.middle.domain.plan.common.enums.MaterialSceneTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanCoverMaterialTypeEnum;
import com.inventory.middle.infra.plan.persistence.condition.plan.*;
import com.inventory.middle.infra.plan.persistence.mapper.plan.PlanMapper;
import com.inventory.middle.infra.plan.persistence.mapper.plan.PlanMaterialMapper;
import com.inventory.middle.infra.plan.persistence.mapper.plan.PlanMaterialParameterMapper;
import com.inventory.middle.infra.plan.persistence.entity.ChangeStatusPlanPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划子域计划配置DAO
 * @date 2021/9/27 17:28
 */
@Component
public class PlanConfigDao {

    @Resource
    private PlanMaterialParameterMapper planMaterialParameterMapper;

    @Resource
    private PlanMapper planMapper;

    @Resource
    private PlanMaterialMapper planMaterialMapper;


    public void batchSavePlanMaterialParam(List<PlanMaterialParameterPO> pos) {
        if (!CollectionUtils.isEmpty(pos)) {
            planMaterialParameterMapper.batchSavePlanMaterialParam(pos);
        }
    }

    public int updatePlanMaterialParam(PlanMaterialParameterPO po) {
        return planMaterialParameterMapper.updateByPrimaryKey(po);
    }

    public PlanMaterialParameterPO selectByMaterialCodeAndLogicalPlantNo(PlanMaterialParamQueryCondition condition) {
        return planMaterialParameterMapper.selectByMaterialCodeAndLogicalPlantNo(condition);
    }

    public List<PlanMaterialParameterPO> batchQueryPlanMaterialParam(List<PlanMaterialParamQueryCondition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return null;
        }
        return planMaterialParameterMapper.batchQueryPlanMaterialParam(conditions);
    }

    public PageInfo<PlanMaterialParameterPO> pageQueryPlanMaterialParam(PlanMaterialParamPageCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getSize());
        List<PlanMaterialParameterPO> list = planMaterialParameterMapper.pageQueryPlanMaterialParam(condition);
        return new PageInfo(list);
    }

    public PlanMaterialParameterPO selectPlanMaterialParameterById(Long id) {
        return planMaterialParameterMapper.selectByPrimaryKey(id);
    }

    public Integer savePlan(PlanPO po) {
        return planMapper.insertSelective(po);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updatePlan(PlanPO po, List<String> currentLogicalPlants) {
        // 更新计划方案
        int result = planMapper.updateByPrimaryKey(po);
        // 如果覆盖了全部物料 需要删除该计划方案之前上传的物料清单
        if (result > 0 && po.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.ALL.getCode())) {
            planMaterialMapper.deletePlanMaterialByPlanId(po.getId(), MaterialSceneTypeEnum.PLAN_MATERIAL_TYPE.getCode());
        }
        // 如果是指定物料 需要将不在逻辑仓范围内的物料清单删除
        if (result > 0 && po.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.APPOINT.getCode()) && !CollectionUtils.isEmpty(currentLogicalPlants)) {
            planMaterialMapper.deletePlanMaterialNotInLogicalPlants(po.getId(), MaterialSceneTypeEnum.PLAN_MATERIAL_TYPE.getCode(), currentLogicalPlants);
        }
        return result;
    }

    public PlanPO queryPlanById(Long id) {
        return planMapper.selectByPrimaryKey(id);
    }

    public List<PlanPO> queryPlanByIds(List<Long> planIds) {
        if (CollectionUtils.isEmpty(planIds)) {
            return null;
        }
        return planMapper.selectPlanByIds(planIds);
    }

    public List<PlanPO> queryPlanByCoverMaterialType(PlanQueryByCoverMaterialTypeCondition condition) {
        return planMapper.selectPlanByCoverMaterialType(condition);
    }

    public List<PlanPO> queryPlanByDemandPlanId(PlanQueryByDemandPlanIdCondition condition) {
        return planMapper.selectPlanByDemandPlanId(condition);
    }

    public List<PlanPO> queryPlanByLogicalPlantNo(PlanQueryByLogicalPlantNoCondition condition) {
        return planMapper.selectPlanByLogicalPlantNo(condition);
    }

    public List<PlanPO> queryPlanByType(PlanQueryByTypeCondition condition) {
        return planMapper.selectPlanByType(condition);
    }

    public PageInfo<PlanPO> pageQueryPlan(PlanPageCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getSize());
        List<PlanPO> list = planMapper.pageQueryPlan(condition);
        return new PageInfo(list);
    }


    public List<PlanMaterialPO> queryPlanMaterialByMaterialCodeAndLogicalNo(List<PlanMaterialQueryCondition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return null;
        }
        return planMaterialMapper.selectByMaterialCodeAndLogicalNo(MaterialSceneTypeEnum.PLAN_MATERIAL_TYPE.getCode(), conditions);
    }

    public List<PlanMaterialPO> queryPlanMaterialByPlanIds(List<Long> planIds) {
        if (CollectionUtils.isEmpty(planIds)) {
            return null;
        }
        return planMaterialMapper.selectByPlanIds(MaterialSceneTypeEnum.PLAN_MATERIAL_TYPE.getCode(), planIds);
    }

    public void batchSavePlanMaterial(List<PlanMaterialPO> planMaterials) {
        if (!CollectionUtils.isEmpty(planMaterials)) {
            //去重
            planMaterials = planMaterials.stream().distinct().collect(Collectors.toList());
            planMaterialMapper.batchInsertPlanMaterial(planMaterials);
        }
    }

    public int batchDeletePlanMaterial(List<PlanMaterialPO> planMaterials) {
        if (CollectionUtils.isEmpty(planMaterials)) {
            return 0;
        }
        return planMaterialMapper.batchDeletePlanMaterial(planMaterials);
    }

    public List<PlanMaterialPO> queryPlanMaterialByPlanId(Long planId, String tenantId, Integer type) {
        return planMaterialMapper.selectByPlanId(planId, tenantId, type);
    }

    public int changeStatus(ChangeStatusPlanPO po) {
        // 根据id更新计划状态
        return planMapper.updateStatusByPrimaryKey(po);
    }

    public List<String> queryLogicalPlantsByMaterial(PlanQueryPlanTransferLogicalPlantsCondition condition) {
        return planMaterialParameterMapper.queryLogicalPlantNoByMaterialCode(condition);
    }

    public List<PlanPO> queryByTenantId(String tenantId) {
        return planMapper.selectByTenantId(tenantId);
    }

    public List<String> queryPlanMaterialByPlanCondition(List<PlanMaterialQueryByPlanCondition> planMaterialQueryByPlanConditions) {
        return planMapper.selectPlanCondition(planMaterialQueryByPlanConditions);
    }
}
