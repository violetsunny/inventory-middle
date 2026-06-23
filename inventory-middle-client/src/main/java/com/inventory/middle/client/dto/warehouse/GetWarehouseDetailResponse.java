package com.inventory.middle.client.dto.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GetWarehouseDetailResponse implements Serializable {

    /**
     * 物理仓库id
     */
    private Long warehouseId;
    /**
     * 物理仓库编号
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
     * 物理仓库负责人名称
     */
    private String ownerName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 省份代码
     */
    private String province;
    /**
     * 市区代码
     */
    private String city;
    /**
     * 区域代码
     */
    private String region;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String lastUpdateTimeStr;

    /**
     * 最后修改用户ID
     */
    private String updateUserId;

}
