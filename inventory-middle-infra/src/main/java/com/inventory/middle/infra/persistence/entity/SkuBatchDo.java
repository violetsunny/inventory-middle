package com.inventory.middle.infra.persistence.entity;

import lombok.Data;
import java.util.Date;

/**
 * SKU 批次数据 DO（product-center 本地沉淀表 product_sku_batch）
 */
@Data
public class SkuBatchDo {

    /** 主键 */
    private Long id;

    /** SKU 编码 */
    private String skuCode;

    /** 批次编码 */
    private String batchCode;

    /** 租户 ID */
    private String tenantId;

    /** 扩展信息（JSON） */
    private String ext;

    /** 操作人 */
    private String updatorId;

    /** 逻辑删除（0=正常，1=已删除） */
    private Integer deleted;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
