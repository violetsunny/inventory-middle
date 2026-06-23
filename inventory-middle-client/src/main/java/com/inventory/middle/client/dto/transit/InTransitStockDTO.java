package com.inventory.middle.client.dto.transit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.inventory.middle.client.enums.InventorySupplyTypeEnum;

import com.inventory.middle.client.enums.MaterialDocRefOrderTypeEnum;
import lombok.Data;

/**
 * @description inventory_supply
 * @author holmes
 * @date 2021-06-16
 */
@Data
public class InTransitStockDTO implements Serializable {

    private static final long serialVersionUID = 3733516743888452378L;

    /**
     * id
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
     * 逻辑仓id
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
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 逻辑仓库类型mark
     */
    private String logicalPlantTypeMark;

    /**
     * 逻辑仓库类型描述
     */
    private String logicalPlantTypeDesc;

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
     * 库存过期日期
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
     * @see InventorySupplyTypeEnum
     */
    private Integer supplyType;

    /**
     * 来源单据类型
     * @see MaterialDocRefOrderTypeEnum
     */
    private Integer sourceOrderType;

    /**
     * 来源单据类型
     */
    private String sourceOrderTypeDesc;

    /**
     * 来源单据编号
     */
    private String sourceOrderNo;

    /**
     * 交运单号
     */
    private String deliveryNo;

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
