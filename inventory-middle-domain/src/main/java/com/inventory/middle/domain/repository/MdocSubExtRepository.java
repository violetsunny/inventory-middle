package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MdocSubExt;
import com.inventory.middle.domain.model.types.MdocSubExtId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证-标签-扩展Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubExtRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MdocSubExt> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证-标签-扩展
     *
     * @param id
     * @return
     */
     MdocSubExt findById(MdocSubExtId id);

    /**
     * 保存
     *
     * @param mdocsubext
     */
    boolean store(MdocSubExt mdocsubext);

    /**
     * 更新
     *
     * @param mdocsubext
     */
    boolean update(MdocSubExt mdocsubext);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MdocSubExtId> ids);
}
