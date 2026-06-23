package com.inventory.middle.domain.model.bo.inventory;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description 库存快照
 * @author kll
 * @date 2021-06-16
 */
@Data
public class InventorySnapshotBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库存快照主键
     */
    private Long id;

    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 物料类目
     */
    private String materialCategoryCode;

    /**
     * 物理仓
     */
    private Long warehouseId;

    /**
     * 逻辑仓
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓库code
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓库类型
     */
    private Integer logicalPlantType;

    /**
     * 存储地点
     */
    private Long locationId;

    /**
     * 存储地点名称
     */
    private String locationNo;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 批次入库时间
     */
    private Date batchTime;

    /**
     * 批次价格
     */
    private BigDecimal batchPrice;

    /**
     * 货币
     */
    private String currency;

    /**
     * 正品库存
     */
    private BigDecimal unrestricted;

    /**
     * 次品库存
     */
    private BigDecimal damaged;

    /**
     * 质检库存
     */
    private BigDecimal inspection;

    /**
     * 计量单位
     */
    private String uom;

    /**
     * 生产日期
     */
    private Date eta;

    /**
     * 过期日期
     */
    private Date dueDate;

    /**
     * 出库价
     */
    private BigDecimal warehousePrice;

    /**
     * 库存所有者
     */
    private Long ownerId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改人id
     */
    private String updatorId;

    /**
     * 删除标识
     */
    private Boolean deleted;

    /**
     * 租户id
     */
    private String tenantId;

}