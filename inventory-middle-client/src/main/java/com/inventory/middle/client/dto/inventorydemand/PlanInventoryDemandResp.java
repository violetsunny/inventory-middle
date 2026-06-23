package com.inventory.middle.client.dto.inventorydemand;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 需求--出库--发货
 *
 * @author vincent.li
 * @date 2021/9/28
 */
@Data
public class PlanInventoryDemandResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 需求id
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
     * 供应类型
     */
    private Integer stockType;

    /**
     * demand类型 1-立即出库，2-计划出库
     */
    private Integer demandType;

    /**
     * 来源单据类型 0-无意义,其他类型待扩展
     */
    private Integer sourceOrderType;

    /**
     * 来源单据编号
     */
    private Integer sourceOrderNo;

    /**
     * 正品数量
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
     * 需求取消日期
     */
    private Date cancelDate;

    /**
     * 需求过期日期
     */
    private Date deadlineDate;

    /**
     * 计划出库时间(plan outbount time)
     */
    private Date pot;

    /**
     * 货币单位
     */
    private String currency;

    /**
     * 出库价
     */
    private BigDecimal warehousePrice;

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
     * 租户id
     */
    private String tenantId;

}