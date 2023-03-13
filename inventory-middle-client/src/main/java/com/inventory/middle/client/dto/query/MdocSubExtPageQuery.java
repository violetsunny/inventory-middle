package com.inventory.middle.client.dto.query;


import lombok.Data;
import top.kdla.framework.dto.PageQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 物料凭证-标签-扩展PageQuery
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */

@Data
public class MdocSubExtPageQuery extends PageQuery {
    
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
     * 批次有效天数
     */
    private Integer validDays;
    
    /**
     * 生产日期
     */
    private LocalDateTime produceDate;
    
    /**
     * 海关编码
     */
    private String hsCode;
    
    /**
     * 下次年检日期yyyy-mm-dd
     */
    private LocalDateTime annualDate;
    
    /**
     * 年检周期天数
     */
    private Integer annualCycleDays;
    
    /**
     * 租户id
     */
    private String tenantId;
    
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
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
}

