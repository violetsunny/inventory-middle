package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存快照 页面展示BO
 *
 * @author holmes
 */
@Data
public class DetailInventorySnapshotResBO implements Serializable {

    /**
     * 管理粒度标题
     */
    private String title;

    /**
     * 管理粒度描述
     */
    private String desc;

    /**
     * 存储地点ID
     */
    private Long locationId;

    /**
     * 逻辑仓ID
     */
    private Long logicalPlantId;

    /**
     * 良品
     */
    private BigDecimal unrestricted;

    /**
     * 残次
     */
    private BigDecimal damaged;

    /**
     * 质检库存
     */
    private BigDecimal inspection;

    /**
     * 批次单价
     */
    private BigDecimal batchPrice;

    /**
     * 货币
     */
    private String currency;

    /**
     * 批次总价
     */
    private BigDecimal batchTotalPrice;

    /**
     * 批次创建时间
     */
    private Date createTime;

    /**
     * 生产日期
     */
    private Date eta;

    /**
     * 过期日期
     */
    private Date dueDate;

    /**
     * 当前粒度 0-逻辑仓 1-存储地点 2-批次
     */
    private Integer type;
}
