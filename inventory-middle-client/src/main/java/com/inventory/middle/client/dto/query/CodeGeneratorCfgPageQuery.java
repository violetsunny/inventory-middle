package com.inventory.middle.client.dto.query;


import lombok.Data;
import top.kdla.framework.dto.PageQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 流水号配置表PageQuery
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */

@Data
public class CodeGeneratorCfgPageQuery extends PageQuery {
    
    /**
     * 主键(自增处理)
     */
    private Long id;
    
    /**
     * 流水号唯一code
     */
    private String code;
    
    /**
     * 流水号名称
     */
    private String name;
    
    /**
     * 当前编号池的纪元
     */
    private Long epoch;
    
    /**
     * 最大流水号
     */
    private String maxValue;
    
    /**
     * 流水号规则
     */
    private String rule;
    
    /**
     * 是否缓存 0：否  ， 1：是
     */
    private Integer isCache;
    
    /**
     * 缓存数目
     */
    private Integer cacheNum;
    
    /**
     * 环境变量值
     */
    private String envValue;
    
    /**
     * 状态：0有效 1无效
     */
    private Integer isDeleted;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
}

