package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.MdocSubMapDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 物料凭证-标签-移动平均价Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Mapper
public interface MdocSubMapMapper extends BaseMapper<MdocSubMapDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<MdocSubMapDo> queryPage(IPage<MdocSubMapDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    MdocSubMapDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<MdocSubMapDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    MdocSubMapDo findById(@Param("id") Long id);
}