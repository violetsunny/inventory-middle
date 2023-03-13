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
 * 物料凭证子表-数量Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("mdoc_sub_quantity")
public class MdocSubQuantityDo implements Serializable {
    
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

