package com.inventory.middle.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 移动平均价历史记录Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("inventory_map_his")
public class InventoryMapHisDo implements Serializable {
    
    /**
     * MAP历史记录主键
     */
    private Long id;
    
    /**
     * map主键
     */
    private Long mapId;
    
    /**
     * map流水号
     */
    private String mapCode;
    
    /**
     * map子流水code
     */
    private String mapSubCode;
    
    /**
     * 物料code
     */
    private String skuCode;
    
    /**
     * 逻辑仓
     */
    private String logicalPlantNo;
    
    /**
     * 价格总和
     */
    private BigDecimal skuPriceTotal;
    
    /**
     * 数量总和
     */
    private BigDecimal skuStockTotal;
    
    /**
     * 移动平均价
     */
    private BigDecimal map;
    
    /**
     * 货币单位
     */
    private String currency;
    
    /**
     * 汇率
     */
    private BigDecimal exchangeRate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人ID
     */
    private String creatorId;
    
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 修改人ID
     */
    private String updatorId;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
    /**
     * 租户ID
     */
    private String tenantId;
    }

