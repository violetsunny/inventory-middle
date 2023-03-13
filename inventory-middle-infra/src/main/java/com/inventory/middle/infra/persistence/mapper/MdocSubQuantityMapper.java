package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.MdocSubQuantityDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 物料凭证子表-数量Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Mapper
public interface MdocSubQuantityMapper extends BaseMapper<MdocSubQuantityDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<MdocSubQuantityDo> queryPage(IPage<MdocSubQuantityDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    MdocSubQuantityDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<MdocSubQuantityDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    MdocSubQuantityDo findById(@Param("id") Long id);
}