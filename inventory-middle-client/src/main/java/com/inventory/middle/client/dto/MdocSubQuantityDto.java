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
 * 物料凭证子表-数量Dto
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubQuantityDto extends BaseModel {
    
    /**
     * 数量信息主键
     */
    private Long id;
    
    /**
     * 物料凭证id
     */
    private Long materialDocId;
    
    /**
     * 物料凭证item
     */
    private Long materialDocItemId;
    
    /**
     * 移动数量
     */
    private BigDecimal adjustQuantity;
    
    /**
     * 计量单位
     */
    private String uom;
    
    /**
     * 不含税单价
     */
    private BigDecimal price;
    
    /**
     * 不含税总价
     */
    private BigDecimal totalPrice;
    
    /**
     * 价税总价
     */
    private BigDecimal totalPriceTax;
    
    /**
     * 税码
     */
    private String taxCode;
    
    /**
     * 税码名称
     */
    private String taxName;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 税额
     */
    private BigDecimal tax;
    
    /**
     * 货币
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
     * 创建人id
     */
    private Long creatorId;
    
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 修改人id
     */
    private Long updatorId;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
    /**
     * 租户id
     */
    private String tenantId;
    
}

