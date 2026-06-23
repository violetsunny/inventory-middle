package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 码申请单明细领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class CodeApplyOrderDetail implements Entity<CodeApplyOrderDetail> {

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
     * 内部唯一码
     */
    private String innerCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public boolean sameIdentityAs(CodeApplyOrderDetail other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
