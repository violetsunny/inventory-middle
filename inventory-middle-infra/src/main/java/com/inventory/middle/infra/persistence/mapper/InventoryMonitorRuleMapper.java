package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 库存预警规则Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Mapper
public interface InventoryMonitorRuleMapper extends BaseMapper<InventoryMonitorRuleDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<InventoryMonitorRuleDo> queryPage(IPage<InventoryMonitorRuleDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    InventoryMonitorRuleDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<InventoryMonitorRuleDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    InventoryMonitorRuleDo findById(@Param("id") Long id);
}