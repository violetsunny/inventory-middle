package com.inventory.middle.infra.plan.persistence.mapper.plan;

import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanDetailPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialPlanDetailMapper {

    /**
     * 新增记录
     *
     * @param record 物料计划执行详情
     * @return 影响行数
     */
    int insert(MaterialPlanDetailPO record);

    /**
     * 批量新增记录
     *
     * @param records 物料计划详情
     * @return 影响行数
     */
    int batchInsert(@Param("records") List<MaterialPlanDetailPO> records);

    /**
     * 根据记录id返回物料计划执行详情
     *
     * @param id 记录id
     * @return 物料计划执行详情
     */
    MaterialPlanDetailPO selectById(Long id);

    /**
     * 根据物料计划实例id返回物料计划执行详情
     *
     * @param materialInstanceId 物料计划实例id
     * @return 物料计划执行详情
     */
    List<MaterialPlanDetailPO> selectByMaterialInstance(Long materialInstanceId);

    /**
     * 根据记录id更新物料计划执行详情
     *
     * @param record 物料计划执行详情
     * @return 影响行数
     */
    int update(MaterialPlanDetailPO record);
}