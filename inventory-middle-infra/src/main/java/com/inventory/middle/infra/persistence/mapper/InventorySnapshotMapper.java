package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.InventorySnapshotDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 库存快照-实时Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Mapper
public interface InventorySnapshotMapper extends BaseMapper<InventorySnapshotDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<InventorySnapshotDo> queryPage(IPage<InventorySnapshotDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    InventorySnapshotDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<InventorySnapshotDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    InventorySnapshotDo findById(@Param("id") Long id);

    /**
     * 上调库存数量
     */
    Integer adjustUp(InventorySnapshotDo inventorySnapshot);

    /**
     * 下调库存数量
     */
    Integer adjustDown(InventorySnapshotDo inventorySnapshot);

    /**
     * 按ID禁用快照
     */
    Integer disableById(InventorySnapshotDo inventorySnapshot);

    /**
     * 查询物料合计（按监控维度）
     */
    List<InventorySnapshotDo> queryMaterialTotal(@Param("params") Map<String, Object> params);

    /**
     * 根据物料编码和逻辑仓查询
     */
    List<InventorySnapshotDo> queryByMaterialAndLogical(@Param("params") Map<String, Object> params);
}
