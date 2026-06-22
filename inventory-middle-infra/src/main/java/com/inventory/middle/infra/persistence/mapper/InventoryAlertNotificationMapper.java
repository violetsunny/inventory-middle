package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.InventoryAlertNotificationDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 库存报警通知记录Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Mapper
public interface InventoryAlertNotificationMapper extends BaseMapper<InventoryAlertNotificationDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<InventoryAlertNotificationDo> queryPage(IPage<InventoryAlertNotificationDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    InventoryAlertNotificationDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<InventoryAlertNotificationDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    InventoryAlertNotificationDo findById(@Param("id") Long id);
}