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
 * 库存报警日志Dto
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryAlertDto extends BaseModel {
    
    /**
     * 预警记录id
     */
    private Long id;
    
    /**
     * 预警规则id
     */
    private Long monitorRuleId;
    
    /**
     * 物料编码
     */
    private String materialCode;
    
    /**
     * 逻辑仓库no
     */
    private String logicalPlantNo;
    
    /**
     * 偏差数量
     */
    private BigDecimal deviate;
    
    /**
     * 报警标识
     */
    private String action;
    
    /**
     * 报警时间
     */
    private LocalDateTime alertDate;
    
    /**
     * 报警状态（处理状态）
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人id
     */
    private String creatorId;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 更新人id
     */
    private String updatorId;
    
    /**
     * 删除标识 0-未删除；1-已删除
     */
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    
}

