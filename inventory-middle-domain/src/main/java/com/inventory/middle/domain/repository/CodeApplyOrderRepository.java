package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.CodeApplyOrder;
import java.util.List;

/**
 * 码申请单Repository接口
 */
public interface CodeApplyOrderRepository {

    /**
     * 根据ID查询
     */
    CodeApplyOrder findById(Long id);

    /**
     * 保存
     */
    boolean store(CodeApplyOrder entity);

    /**
     * 更新
     */
    boolean update(CodeApplyOrder entity);

    /**
     * 根据ID列表查询
     */
    List<CodeApplyOrder> findByIds(List<Long> ids);

    /**
     * 按条件查询
     */
    List<CodeApplyOrder> listByCondition(CodeApplyOrderQueryParam queryParam);
}
