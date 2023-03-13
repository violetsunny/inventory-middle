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
 * 库存-供给Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("inventory_supply")
public class InventorySupplyDo implements Serializable {
    
    /**
     * 供应id
     */
    private Long id;
    
    /**
     * 物料code
     */
    private String materialCode;
    
    /**
     * 批次号
     */
    private String batchNo;
    
    /**
     * 批次入库时间
     */
    private LocalDateTime batchTime;
    
    /**
     * 库存所有者
     */
    private String owner;
    
    /**
     * 所在仓库
     */
    private String logicalPlantNo;
    
    /**
     * 存储地点
     */
    private String storageLocationNo;
    
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
    private LocalDateTime productDate;
    
    /**
     * 最早可用日期
     */
    private LocalDateTime availableDate;
    
    /**
     * 过期日期
     */
    private LocalDateTime dueDate;
    
    /**
     * 批次价格
     */
    private BigDecimal batchPrice;
    
    /**
     * 货币单位
     */
    private String currency;
    
    /**
     * 供应类型1-立即入库，2-在途转入库，3-计划转入库
     */
    private Integer supplyType;
    
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
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    }

