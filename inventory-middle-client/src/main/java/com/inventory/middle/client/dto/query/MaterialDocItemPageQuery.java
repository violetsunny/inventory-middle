package com.inventory.middle.client.dto.query;


import lombok.Data;
import top.kdla.framework.dto.PageQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 物料凭证-itemPageQuery
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */

@Data
public class MaterialDocItemPageQuery extends PageQuery {
    
    /**
     * 物料凭证itemId
     */
    private Long id;
    
    /**
     * 物料凭证id
     */
    private Long materialDocId;
    
    /**
     * 物料code
     */
    private String materialCode;
    
    /**
     * 批次号
     */
    private String batchNo;
    
    /**
     * 出入库类型1-入库,2-出库
     */
    private Integer itemCategory;
    
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

