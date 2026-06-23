package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 码申请单领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class CodeApplyOrder implements Entity<CodeApplyOrder> {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 申请渠道
     */
    private Integer channel;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 申请单编码
     */
    private String orderNo;

    /**
     * 所有者-厂商
     */
    private String ownerId;

    /**
     * 审批状态
     */
    private Integer approvalStatus;

    /**
     * 申请数量
     */
    private Integer applyNum;

    /**
     * 批准数量
     */
    private Integer approvedNum;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean sameIdentityAs(CodeApplyOrder other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
