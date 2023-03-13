package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MdocSubFinance;
import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证-标签-财务Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MdocSubFinanceRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MdocSubFinance> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证-标签-财务
     *
     * @param id
     * @return
     */
     MdocSubFinance findById(MdocSubFinanceId id);

    /**
     * 保存
     *
     * @param mdocsubfinance
     */
    boolean store(MdocSubFinance mdocsubfinance);

    /**
     * 更新
     *
     * @param mdocsubfinance
     */
    boolean update(MdocSubFinance mdocsubfinance);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MdocSubFinanceId> ids);
}
