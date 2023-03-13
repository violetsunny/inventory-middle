package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import com.inventory.middle.domain.model.types.MdocSubMaterialId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证子表-物料信息Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMaterialRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MdocSubMaterial> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证子表-物料信息
     *
     * @param id
     * @return
     */
     MdocSubMaterial findById(MdocSubMaterialId id);

    /**
     * 保存
     *
     * @param mdocsubmaterial
     */
    boolean store(MdocSubMaterial mdocsubmaterial);

    /**
     * 更新
     *
     * @param mdocsubmaterial
     */
    boolean update(MdocSubMaterial mdocsubmaterial);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MdocSubMaterialId> ids);
}
