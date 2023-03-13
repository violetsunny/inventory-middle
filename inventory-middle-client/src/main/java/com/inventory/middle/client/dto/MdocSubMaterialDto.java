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
 * 物料凭证子表-物料信息Dto
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubMaterialDto extends BaseModel {
    
    /**
     * 物料信息主键
     */
    private Long id;
    
    /**
     * 物料凭证ID
     */
    private Long materialDocId;
    
    /**
     * 物料凭证item
     */
    private Long materialDocItemId;
    
    /**
     * 物料code
     */
    private String materialCode;
    
    /**
     * 物料名称
     */
    private String materialName;
    
    /**
     * 物料品类
     */
    private String materialCategoryCode;
    
    /**
     * 物料重量
     */
    private BigDecimal materialWeight;
    
    /**
     * 物料重量单位
     */
    private String weightUnit;
    
    /**
     * 物料体积
     */
    private BigDecimal materialVolume;
    
    /**
     * 物料体积单位
     */
    private String volumeUnit;
    
    /**
     * 评估类
     */
    private String valuation;
    
    /**
     * 备注1
     */
    private String remark1;
    
    /**
     * 备注2
     */
    private String remark2;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 创建人ID
     */
    private Long creatorId;
    
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 修改人ID
     */
    private Long updatorId;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
}

