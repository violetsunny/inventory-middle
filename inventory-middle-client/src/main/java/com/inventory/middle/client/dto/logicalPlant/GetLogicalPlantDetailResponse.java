package com.inventory.middle.client.dto.logicalPlant;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 逻辑仓详情返回对象
 */
@Data
public class GetLogicalPlantDetailResponse implements Serializable {
    /**
     * 逻辑仓的id
     */
    private Long logicalPlantId;
    /**
     * 逻辑仓所属的租户的id
     */
    private String tenantId;
    /**
     * 逻辑仓的编号
     */
    private String logicalPlantNo;
    /**
     * 逻辑仓的名称
     */
    private String logicalPlantName;
    /**
     * 逻辑仓的类型
     */
    private Integer type;
    /**
     * 逻辑仓的类型描述
     */
    private String typeDescription;
    /**
     * 逻辑仓所属的公司代码
     */
    private String companyCode;
    /**
     * 逻辑仓关联的物理仓的id
     */
    private Long warehouseId;
    /**
     * 逻辑仓关联的物理仓的No
     */
    private String warehouseNo;
    /**
     * 逻辑仓关联的物理仓的名称
     */
    private String warehouseName;
    /**
     * 省份
     */
    private String province;
    /**
     * 市区
     */
    private String city;
    /**
     * 区域
     */
    private String region;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String lastUpdateTimeStr;

    private String updateUserId;
}
