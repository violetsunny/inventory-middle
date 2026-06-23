package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.CodeApplyApprovalRecord;
import java.util.List;

/**
 * 码申请审批记录Repository接口
 */
public interface CodeApplyApprovalRecordRepository {

    /**
     * 根据ID查询
     */
    CodeApplyApprovalRecord findById(Long id);

    /**
     * 保存
     */
    boolean store(CodeApplyApprovalRecord entity);

    /**
     * 更新
     */
    boolean update(CodeApplyApprovalRecord entity);

    /**
     * 根据ID列表查询
     */
    List<CodeApplyApprovalRecord> findByIds(List<Long> ids);
}
