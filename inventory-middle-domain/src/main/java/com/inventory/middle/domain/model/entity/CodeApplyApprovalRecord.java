package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 码申请审批记录领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class CodeApplyApprovalRecord implements Entity<CodeApplyApprovalRecord> {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 申请单id
     */
    private Long orderId;

    /**
     * 申请单编码
     */
    private String orderNo;

    /**
     * 审批人id
     */
    private String approvalUserId;

    /**
     * 审批人姓名
     */
    private String approvalUserName;

    /**
     * 审批状态
     */
    private Integer approvalStatus;

    /**
     * 审批意见
     */
    private String approvalComment;

    /**
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public boolean sameIdentityAs(CodeApplyApprovalRecord other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
