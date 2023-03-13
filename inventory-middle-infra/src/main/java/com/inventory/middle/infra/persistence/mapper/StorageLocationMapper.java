package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.StorageLocationDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 存储地点表Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:24
 */
@Mapper
public interface StorageLocationMapper extends BaseMapper<StorageLocationDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<StorageLocationDo> queryPage(IPage<StorageLocationDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    StorageLocationDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<StorageLocationDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    StorageLocationDo findById(@Param("id") Long id);
}