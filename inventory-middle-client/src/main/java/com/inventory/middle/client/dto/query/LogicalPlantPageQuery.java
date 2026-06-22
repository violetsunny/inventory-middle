package com.inventory.middle.client.dto.query;


import lombok.Data;
import top.kdla.framework.dto.PageQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 逻辑仓库表PageQuery
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */

@Data
public class LogicalPlantPageQuery extends PageQuery {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 创建该逻辑仓库的租户id
     */
    private String tenantId;
    
    /**
     * 逻辑仓库编码
     */
    private String logicalPlantNo;
    
    /**
     * 逻辑仓库名称
     */
    private String logicalPlantName;
    
    /**
     * 逻辑仓库类型
     */
    private Integer type;
    
    /**
     * 公司主体代码
     */
    private String companyCode;
    
    /**
     * 该逻辑仓所属的物理仓no
     */
    private String warehouseNo;
    
    /**
     * 省
     */
    private String province;
    
    /**
     * 市
     */
    private String city;
    
    /**
     * 区
     */
    private String region;
    
    /**
     * 逻辑仓库地址
     */
    private String address;
    
    /**
     * 创建人
     */
    private String creatorId;
    
    /**
     * 更新人
     */
    private String updatorId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 删除标识
     */
    private Integer deleted;
    
}

