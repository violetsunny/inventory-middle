package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MdocSubInOut;
import com.inventory.middle.domain.model.types.MdocSubInOutId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证子表-出入库信息Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubInOutRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MdocSubInOut> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证子表-出入库信息
     *
     * @param id
     * @return
     */
     MdocSubInOut findById(MdocSubInOutId id);

    /**
     * 保存
     *
     * @param mdocsubinout
     */
    boolean store(MdocSubInOut mdocsubinout);

    /**
     * 更新
     *
     * @param mdocsubinout
     */
    boolean update(MdocSubInOut mdocsubinout);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MdocSubInOutId> ids);
}
