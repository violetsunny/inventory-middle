package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 备件流转码领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class Code implements Entity<Code> {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 业务编号
     */
    private String businessNo;

    /**
     * 源单号
     */
    private String sourceNo;

    /**
     * 内部唯一码
     */
    private String innerCode;

    /**
     * 码类型
     */
    private String type;

    /**
     * 码值
     */
    private String code;

    /**
     * 发布者
     */
    private String publisher;

    /**
     * 前一任持有者
     */
    private String preOwner;

    /**
     * 当前持有者
     */
    private String currentOwner;

    /**
     * 状态
     */
    private String status;

    /**
     * 扩展字段1
     */
    private String extendField1;

    /**
     * 扩展字段2
     */
    private String extendField2;

    /**
     * 扩展参数
     */
    private String extendParam;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updatorId;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean sameIdentityAs(Code other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
