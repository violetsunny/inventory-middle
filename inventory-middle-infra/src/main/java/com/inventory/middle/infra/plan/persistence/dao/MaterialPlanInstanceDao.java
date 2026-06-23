package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.infra.plan.persistence.condition.MaterialPlanInstanceCondition;
import com.inventory.middle.infra.plan.persistence.mapper.MaterialPlanDetailMapper;
import com.inventory.middle.infra.plan.persistence.mapper.MaterialPlanInstanceMapper;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstancePO;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstanceWithPlanPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Repository of {@link MaterialPlanInstancePO}
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Repository
public class MaterialPlanInstanceDao {

    @Resource
    private MaterialPlanInstanceMapper materialPlanInstanceMapper;

    @Resource
    private MaterialPlanDetailMapper materialPlanDetailMapper;

    public int insert(MaterialPlanInstancePO record) {
        return materialPlanInstanceMapper.insert(record);
    }

    public MaterialPlanInstancePO selectById(Long id, String tenantId) {
        return materialPlanInstanceMapper.selectById(id, tenantId);
    }

    public int update(MaterialPlanInstancePO record) {
        Checker.notNull(record, "record cannot be null");
        Checker.notNull(record.getId(), "record id cannot be null");
        return materialPlanInstanceMapper.update(record);
    }

    public long count(MaterialPlanInstanceCondition condition) {
        return materialPlanInstanceMapper.count(condition);
    }

    public List<MaterialPlanInstanceWithPlanPO> selectByCondition(MaterialPlanInstanceCondition condition) {
        if (CollectionUtils.isEmpty(condition.getSorts())) {
            condition.appendDesc("create_time");
        }
        return materialPlanInstanceMapper.selectByCondition(condition);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMaterialPlanInstance(MaterialPlanInstancePO materialPlanInstance,
                                         List<MaterialPlanDetailPO> materialPlanDetails) {
        // 保存物料计划实例
        materialPlanInstanceMapper.insert(materialPlanInstance);
        if (CollectionUtils.isNotEmpty(materialPlanDetails)) {
            // 保存物料计划详情
            materialPlanDetails.forEach(detail ->
                    detail.setMaterialInstanceId(materialPlanInstance.getId())
            );
            materialPlanDetailMapper.batchInsert(materialPlanDetails);
        }
    }

    public MaterialPlanInstancePO selectLatestByMaterial(String materialCode, String logicalPlantNo,
                                                         String tenantId, Integer geneType) {
        return materialPlanInstanceMapper.selectLatestByMaterial(materialCode, logicalPlantNo, tenantId, geneType);
    }

    public MaterialPlanInstancePO selectRootByAccordingId(Long materialInstanceId, String tenantId) {
        return materialPlanInstanceMapper.selectRootByAccordingId(materialInstanceId, tenantId);
    }

}
