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
 * 物理仓库表Do
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@TableName("warehouse")
public class WarehouseDo implements Serializable {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 创建该物理仓库的租户id
     */
    private String tenantId;
    
    /**
     * 物理仓库编码
     */
    private String warehouseNo;
    
    /**
     * 物理仓库名称
     */
    private String warehouseName;
    
    /**
     * 物理仓类型(1-真实,0-虚拟)
     */
    private Integer warehouseType;
    
    /**
     * 责任人名称
     */
    private String ownerName;
    
    /**
     * 仓库电话
     */
    private String phone;
    
    /**
     * 仓库地址
     */
    private String address;
    
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
     * 备注
     */
    private String remark;
    
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

