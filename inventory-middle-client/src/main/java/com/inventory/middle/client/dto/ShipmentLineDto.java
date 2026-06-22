package com.inventory.middle.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import top.kdla.framework.dto.BaseModel;


/**
 * 交运单明细Dto
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class ShipmentLineDto extends BaseModel {
    
    /**
     * 交运单明细id
     */
    private Long id;
    
    /**
     * 来源单据编号
     */
    private String sourceOrderNo;
    
    /**
     * 来源单号类型
     */
    private String sourceOrderType;
    
    /**
     * 交运单id
     */
    private Long shipmentId;
    
    /**
     * 交运单号
     */
    private String shipmentNo;
    
    /**
     * 物料编码
     */
    private String materialCode;
    
    /**
     * 外部编码
     */
    private String externalCode;
    
    /**
     * 交运数量
     */
    private BigDecimal quantity;
    
    /**
     * 实际交运数量
     */
    private BigDecimal actualQuantity;
    
    /**
     * 已开票数量
     */
    private BigDecimal invoicedQuantity;
    
    /**
     * 成本中心
     */
    private String costCenterCode;
    
    /**
     * 会计科目
     */
    private String accountingCode;
    
    /**
     * 收货逻辑仓库no
     */
    private String receivingLogicalPlantNo;
    
    /**
     * 收货物理仓库no
     */
    private String receivingWarehouseNo;
    
    /**
     * 收货存储位置no
     */
    private String receivingStorageLocationNo;
    
    /**
     * 收货批次号
     */
    private String receivingBatchNo;
    
    /**
     * 收货公司主体
     */
    private String receivingCompanyCode;
    
    /**
     * 发货逻辑仓库no
     */
    private String deliveryLogicalPlantNo;
    
    /**
     * 发货物理仓库no
     */
    private String deliveryWarehouseNo;
    
    /**
     * 发货存储位置no
     */
    private String deliveryStorageLocationNo;
    
    /**
     * 发货批次号
     */
    private String deliveryBatchNo;
    
    /**
     * 发货公司主体
     */
    private String deliveryCompanyCode;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人id
     */
    private String creatorId;
    
    /**
     * 修改人id
     */
    private String updatorId;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    
}

