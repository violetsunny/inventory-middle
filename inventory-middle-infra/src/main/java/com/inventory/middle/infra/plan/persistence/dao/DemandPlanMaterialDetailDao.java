package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.domain.plan.common.constants.PlanCommonConstants;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialDetailMapper;
import com.inventory.middle.infra.plan.persistence.condition.plan.DemandPlanMaterialDetailReqCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Danny.Lee
 * @date 2021/10/9
 */
@Repository
public class DemandPlanMaterialDetailDao {

    @Resource
    private DemandPlanMaterialDetailMapper demandPlanMaterialDetailMapper;
    
    // TODO: PlanConfigDao 尚未迁移,待迁移后取消注释
    // @Resource
    // private PlanConfigDao planConfigDao;

    public List<DemandPlanMaterialDetailPO> findMaterialDemandDetails(String materialCode,
                                                                      String logicalPlantNo,
                                                                      String tenantId) {
        DemandPlanMaterialDetailPO condition = new DemandPlanMaterialDetailPO();
        condition.setMaterialCode(materialCode);
        condition.setLogicalPlantNo(logicalPlantNo);
        condition.setTenantId(tenantId);
        return demandPlanMaterialDetailMapper.selectByMultiple(condition);
    }


    /**
     * 根据计划id查询
     *
     * @param condition
     * @param isFuture
     * @return
     */
    public List<DemandPlanMaterialDetailPO> findDetailsByCondition(List<DemandPlanMaterialDetailReqCondition> condition, boolean isFuture) {

        List<DemandPlanMaterialDetailPO> resultList = demandPlanMaterialDetailMapper.findDetailsByCondition(condition);
        if (isFuture) {
            resultList = resultList.stream().filter(demandPlanMaterialDetailPO -> {
                return demandPlanMaterialDetailPO.getPlanDate().getTime() >= DateUtils.getStartOfDay(new Date()).getTime();
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    public int batchInsert(List<DemandPlanMaterialDetailPO> records) {
        return demandPlanMaterialDetailMapper.batchInsert(records);
    }

    public int updateAmountBatch(List<DemandPlanMaterialDetailPO> records) {
        return demandPlanMaterialDetailMapper.updateAmountBatch(records);
    }

    /**
     * 批量插入更行，带事务
     * @param insertList
     * @param updateList
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertAndUpdate(List<DemandPlanMaterialDetailPO> insertList, List<DemandPlanMaterialDetailPO> updateList,List<PlanMaterialPO> planMaterials) {
        if (CollectionUtils.isNotEmpty(insertList)) {
            this.batchInsert(insertList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            this.updateAmountBatch(updateList);
        }
        // TODO: PlanConfigDao 尚未迁移,待迁移后取消注释
        // if (CollectionUtils.isNotEmpty(planMaterials)){
        //     planConfigDao.batchSavePlanMaterial(planMaterials);
        // }
    }


    public List<DemandPlanMaterialDetailPO> selectByParams(DemandPlanMaterialDetailPO record) {
        return demandPlanMaterialDetailMapper.selectByMultiple(record);
    }

    /**
     * 查询所有有效物料
     * 根据 物料编码+逻辑仓+租户id 去重
     * @return
     */
    public List<DemandPlanMaterialDetailPO> queryAllMaterial() {
        List<DemandPlanMaterialDetailPO> detailPOList = demandPlanMaterialDetailMapper.queryAllMaterial();
        return detailPOList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(p -> p.getMaterialCode() + PlanCommonConstants.DELIMITER_COMMA + p.getLogicalPlantNo()
                                + PlanCommonConstants.DELIMITER_COMMA + p.getTenantId()))), ArrayList::new));
    }
}
