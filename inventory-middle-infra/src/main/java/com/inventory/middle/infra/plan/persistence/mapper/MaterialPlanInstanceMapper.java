package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.MaterialPlanInstanceCondition;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstancePO;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstanceWithPlanPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper of {@link MaterialPlanInstancePO}
 */
public interface MaterialPlanInstanceMapper {

    /**
     * 新增记录
     *
     * @param record 物料计划实例
     * @return 影响行数
     */
    int insert(MaterialPlanInstancePO record);


    /**
     * 批量新增记录
     *
     * @param records 物料计划实例
     * @return 影响行数
     */
    int batchInsert(@Param("records") List<MaterialPlanInstancePO> records);

    /**
     * 根据记录id返回物料计划执行实例
     *
     * @param id       记录id
     * @param tenantId 租户id
     * @return 物料计划执行实例
     */
    MaterialPlanInstancePO selectById(@Param("id") Long id, @Param("tenantId") String tenantId);

    /**
     * 根据记录id更新物料计划执行实例
     *
     * @param record 物料计划执行实例
     * @return 影响行数
     */
    int update(MaterialPlanInstancePO record);

    /**
     * 根据{@link MaterialPlanInstanceCondition}查询物料计划执行实例
     *
     * @param condition 物料计划执行实例查询条件
     * @return 统计值
     */
    long count(MaterialPlanInstanceCondition condition);

    /**
     * 根据{@link MaterialPlanInstanceCondition}查询物料计划执行实例
     *
     * @param condition 物料计划执行实例查询条件
     * @return 物料计划执行实例
     */
    List<MaterialPlanInstanceWithPlanPO> selectByCondition(MaterialPlanInstanceCondition condition);

    /**
     * 根据物料信息查询最近一次物料计划实例
     *
     * @param materialCode   物料编码
     * @param logicalPlantNo 逻辑仓编码
     * @param tenantId       租户id
     * @param geneType       产出类型
     * @return 物料计划实例
     */
    MaterialPlanInstancePO selectLatestByMaterial(@Param("materialCode") String materialCode,
                                                  @Param("logicalPlantNo") String logicalPlantNo,
                                                  @Param("tenantId") String tenantId,
                                                  @Param("geneType") Integer geneType);

    /**
     * 根据物料id查询所在树的相关物料执行实例
     *
     * @param id 物料编码
     * @return 物料计划实例集合
     */
    MaterialPlanInstancePO selectRootByAccordingId(@Param("id") Long id, @Param("tenantId") String tenantId);
}