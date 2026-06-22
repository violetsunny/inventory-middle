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
 * 库存预警规则明细Dto
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryMonitorRuleLineDto extends BaseModel {
    
    /**
     * 规则明细id
     */
    private Long id;
    
    /**
     * 预警规则id
     */
    private Long monitorRuleId;
    
    /**
     * 预警维度
     */
    private String monitorDimension;
    
    /**
     * 预警对象
     */
    private String monitorObject;
    
    /**
     * 上限
     */
    private BigDecimal monitorCeil;
    
    /**
     * 下限
     */
    private BigDecimal monitorFloor;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人id
     */
    private Long creatorId;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 更新人id
     */
    private Long updatorId;
    
    /**
     * 删除标识 0-未删除；1-已删除
     */
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    
}

