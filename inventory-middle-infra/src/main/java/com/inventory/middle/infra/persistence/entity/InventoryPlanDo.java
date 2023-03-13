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
 * 库存-计划Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("inventory_plan")
public class InventoryPlanDo implements Serializable {
    
    /**
     * 在途id
     */
    private Long id;
    
    /**
     * 物料code
     */
    private String materialCode;
    
    /**
     * 逻辑仓库no
     */
    private String logicalPlantNo;
    
    /**
     * 存储地点no
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
     * 过期日期
     */
    private LocalDateTime dueDate;
    
    /**
     * 价格
     */
    private BigDecimal price;
    
    /**
     * 货币单位
     */
    private String currency;
    
    /**
     * 在途类型1-计划入库 2-计划出库
     */
    private Integer planType;
    
    /**
     * 计划编号
     */
    private String planNo;
    
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

