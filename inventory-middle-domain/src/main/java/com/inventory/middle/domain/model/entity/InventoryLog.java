package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 库存操作日志领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryLog implements Entity<InventoryLog> {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 日志模块
     */
    private String logModule;

    /**
     * 日志动作
     */
    private String logAction;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public boolean sameIdentityAs(InventoryLog other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
