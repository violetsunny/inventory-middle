package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.CodeApplyOrderDetail;
import java.util.List;

/**
 * 码申请单明细Repository接口
 */
public interface CodeApplyOrderDetailRepository {

    /**
     * 根据ID查询
     */
    CodeApplyOrderDetail findById(Long id);

    /**
     * 保存
     */
    boolean store(CodeApplyOrderDetail entity);

    /**
     * 更新
     */
    boolean update(CodeApplyOrderDetail entity);

    /**
     * 根据ID列表查询
     */
    List<CodeApplyOrderDetail> findByIds(List<Long> ids);
}
