/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * @author dongguo
 */
@Data
public class MaterialLogicalPlantRefDTO implements Serializable {

    private static final long serialVersionUID = -7148398993366273721L;

    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private String creatorId;

    /**
     * 更新人ID
     */
    private String updatorId;

    /**
     * 删除状态
     */
    private Integer deleted;


}
