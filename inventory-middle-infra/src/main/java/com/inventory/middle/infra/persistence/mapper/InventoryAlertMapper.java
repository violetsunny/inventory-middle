package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.InventoryAlertDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 库存报警日志Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Mapper
public interface InventoryAlertMapper extends BaseMapper<InventoryAlertDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<InventoryAlertDo> queryPage(IPage<InventoryAlertDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    InventoryAlertDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<InventoryAlertDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    InventoryAlertDo findById(@Param("id") Long id);
}