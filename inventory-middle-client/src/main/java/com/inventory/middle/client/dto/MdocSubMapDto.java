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
 * 物料凭证-标签-移动平均价Dto
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubMapDto extends BaseModel {
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 物料凭证id
     */
    private Long materialDocId;
    
    /**
     * 物料凭证itemId
     */
    private Long materialDocItemId;
    
    /**
     * map流水code
     */
    private String mapCode;
    
    /**
     * map子流水号
     */
    private String mapSubCode;
    
    /**
     * map流水状态
     */
    private Integer mapStatus;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    
    /**
     * 创建人id
     */
    private Long creatorId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 修改人id
     */
    private Long updatorId;
    
}

