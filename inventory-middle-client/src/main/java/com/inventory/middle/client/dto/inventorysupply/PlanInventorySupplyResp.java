package com.inventory.middle.client.dto.inventorysupply;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 远期计划入supply查询返回
 *
 * @author vincent.li
 * @date 2021/9/28
 */
@Data
public class PlanInventorySupplyResp implements Serializable {

    /**
     * 供应id
     */
    private Long id;

    /**
     * 物料凭证id
     */
    private Long materialDocId;

    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 物料类目
     */
    private String materialCategoryCode;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 批次入库时间
     */
    private Date batchTime;

    /**
     * 库存所有者
     */
    private Long ownerId;
    /**
     * 物理仓
     */
    private Long warehouseId;
    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓库code
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓库name
     */
    private String logicalPlantName;

    /**
     * 逻辑仓库类型
     */
    private Integer logicalPlantType;

    /**
     * 存储地点id
     */
    private Long locationId;

    /**
     * 存储地点编码
     */
    private String locationNo;

    /**
     * 存储地点名称
     */
    private String locationName;
    /**
     * 良品数量
     */
    private BigDecimal unrestricted;

    /**
     * 次品数量
     */
    private BigDecimal damaged;

    /**
     * 质检数量
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
     * 货币单位
     */
    private String currency;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 批次价格
     */
    private BigDecimal batchPrice;

    /**
     * 库存类型
     */
    private Integer stockType;

    /**
     * supply类型
     */
    private Integer supplyType;

    /**
     * 来源单据类型
     */
    private Integer sourceOrderType;

    /**
     * 来源单据编号
     */
    private String sourceOrderNo;

    /**
     * 删除标识
     */
    private Integer deleted;

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

}
