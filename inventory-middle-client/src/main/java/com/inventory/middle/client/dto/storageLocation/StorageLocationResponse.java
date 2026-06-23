package com.inventory.middle.client.dto.storageLocation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StorageLocationResponse implements Serializable {

    private static final long serialVersionUID = -353362474883338655L;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 存储地点id
     */
    private Long storageLocationId;
    /**
     * 存储地点编号
     */
    private String storageLocationNo;

    /**
     * 存储地点类型
     */
    private Integer locationType;

    /**
     * 存储地点类型描述
     */
    private String locationTypeDesc;

    /**
     * 存储地点类型编码
     */
    private String locationTypeMark;

    /**
     * 特殊存储地点类型编码
     */
    private String specialStorageLocationMark;
    /**
     * 描述
     */
    private String description;
    /**
     * 存储地点的位置
     */
    private String storageLocationPosition;

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

}
