package com.inventory.middle.infra.plan.persistence.mapper.plan;

import com.inventory.middle.infra.plan.persistence.entity.PlanInstancePO;

/**
 * Mapper of {@link PlanInstancePO}
 */
public interface PlanInstanceMapper {

    /**
     * 新增记录
     *
     * @param record 计划执行实例
     * @return 影响行数
     */
    int insert(PlanInstancePO record);

    /**
     * 根据记录id返回计划执行实例
     *
     * @param id 记录id
     * @return 计划执行实例
     */
    PlanInstancePO selectById(Long id);

    /**
     * 根据计划id返回最新的计划执行实例
     *
     * @param id 记录id
     * @return 计划执行实例
     */
    PlanInstancePO selectLatestByPlanId(Long id);

    /**
     * 根据记录id更新计划执行实例
     *
     * @param record 计划执行实例
     * @return 影响行数
     */
    int update(PlanInstancePO record);
}