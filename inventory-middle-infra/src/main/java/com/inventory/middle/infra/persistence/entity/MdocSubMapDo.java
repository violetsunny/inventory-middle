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
 * 物料凭证-标签-移动平均价Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("mdoc_sub_map")
public class MdocSubMapDo implements Serializable {
    
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

