package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MdocSubMap;
import com.inventory.middle.domain.model.types.MdocSubMapId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证-标签-移动平均价Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMapRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MdocSubMap> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证-标签-移动平均价
     *
     * @param id
     * @return
     */
     MdocSubMap findById(MdocSubMapId id);

    /**
     * 保存
     *
     * @param mdocsubmap
     */
    boolean store(MdocSubMap mdocsubmap);

    /**
     * 更新
     *
     * @param mdocsubmap
     */
    boolean update(MdocSubMap mdocsubmap);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MdocSubMapId> ids);
}
