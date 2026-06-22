package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import com.inventory.middle.domain.model.types.MdocSubQuantityId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证子表-数量Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubQuantityRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MdocSubQuantity> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证子表-数量
     *
     * @param id
     * @return
     */
     MdocSubQuantity findById(MdocSubQuantityId id);

    /**
     * 保存
     *
     * @param mdocsubquantity
     */
    boolean store(MdocSubQuantity mdocsubquantity);

    /**
     * 更新
     *
     * @param mdocsubquantity
     */
    boolean update(MdocSubQuantity mdocsubquantity);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MdocSubQuantityId> ids);
}
