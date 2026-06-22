package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.ShipmentLineDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 交运单明细Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper
public interface ShipmentLineMapper extends BaseMapper<ShipmentLineDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<ShipmentLineDo> queryPage(IPage<ShipmentLineDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    ShipmentLineDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<ShipmentLineDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ShipmentLineDo findById(@Param("id") Long id);
}