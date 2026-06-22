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
 * 库存快照-实时Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("inventory_snapshot")
public class InventorySnapshotDo implements Serializable {
    
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
     * 逻辑仓库no
     */
    private String logicalPlantNo;
    
    /**
     * 存储地点no
     */
    private String storageLocationNo;
    
    /**
     * 批次号
     */
    private String batchNo;
    
    /**
     * 批次入库时间
     */
    private LocalDateTime batchTime;
    
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
    private LocalDateTime productDate;
    
    /**
     * 过期日期
     */
    private LocalDateTime dueDate;
    
    /**
     * 库存所有者
     */
    private String owner;
    
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

