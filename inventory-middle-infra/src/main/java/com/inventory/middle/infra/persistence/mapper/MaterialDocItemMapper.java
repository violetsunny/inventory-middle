package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.MaterialDocItemDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 物料凭证-itemMapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Mapper
public interface MaterialDocItemMapper extends BaseMapper<MaterialDocItemDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<MaterialDocItemDo> queryPage(IPage<MaterialDocItemDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    MaterialDocItemDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<MaterialDocItemDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    MaterialDocItemDo findById(@Param("id") Long id);
}