package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.model.types.MaterialDocMainId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证主表Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocMainRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MaterialDocMain> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证主表
     *
     * @param id
     * @return
     */
     MaterialDocMain findById(MaterialDocMainId id);

    /**
     * 保存
     *
     * @param materialdocmain
     */
    boolean store(MaterialDocMain materialdocmain);

    /**
     * 更新
     *
     * @param materialdocmain
     */
    boolean update(MaterialDocMain materialdocmain);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MaterialDocMainId> ids);
}
