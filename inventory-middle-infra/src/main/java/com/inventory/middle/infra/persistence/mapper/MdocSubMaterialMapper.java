package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.MdocSubMaterialDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 物料凭证子表-物料信息Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Mapper
public interface MdocSubMaterialMapper extends BaseMapper<MdocSubMaterialDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<MdocSubMaterialDo> queryPage(IPage<MdocSubMaterialDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    MdocSubMaterialDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<MdocSubMaterialDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    MdocSubMaterialDo findById(@Param("id") Long id);
}