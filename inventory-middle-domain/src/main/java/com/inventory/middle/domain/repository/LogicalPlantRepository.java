package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 逻辑仓库表Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface LogicalPlantRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<LogicalPlant> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取逻辑仓库表
     *
     * @param id
     * @return
     */
     LogicalPlant findById(LogicalPlantId id);

    /**
     * 保存
     *
     * @param logicalplant
     */
    boolean store(LogicalPlant logicalplant);

    /**
     * 更新
     *
     * @param logicalplant
     */
    boolean update(LogicalPlant logicalplant);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<LogicalPlantId> ids);
}
