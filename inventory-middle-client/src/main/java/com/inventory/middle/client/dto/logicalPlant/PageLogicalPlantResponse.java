package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.storageLocation.StorageLocationResponse;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分页查询逻辑仓返回体
 * @author hjs
 */
@Data
public class PageLogicalPlantResponse implements Serializable {

    /**
     * 逻辑仓的id
     */
    private Long logicalPlantId;
    /**
     * 逻辑仓的编号
     */
    private String logicalPlantNo;
    /**
     * 逻辑仓的名称
     */
    private String logicalPlantName;
    /**
     * 逻辑仓所属公司code
     */
    private String companyCode;
    /**
     * 逻辑仓关联的物理仓库id
     */
    private Long warehouseId;
    /**
     * 逻辑仓关联的物理仓库编码
     */
    private String warehouseNo;
    /**
     * 逻辑仓所属的物理仓的名称
     */
    private String warehouseName;
    /**
     * 逻辑仓所属的物理仓的省
     */
    private String warehouseProvince;
    /**
     * 逻辑仓所属的物理仓的市
     */
    private String warehouseCity;
    /**
     * 逻辑仓所属的物理仓的区
     */
    private String warehouseRegion;
    /**
     * 逻辑仓所属的物理仓的地址
     */
    private String warehouseAddress;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 该逻辑仓下的存储地点列表
     */
    private List<StorageLocationResponse> slList;
    /**
     * 该逻辑仓下存储地点的数量
     */
    private Integer slCount;

    /**
     * 逻辑仓库省
     */
    private String province;
    /**
     * 逻辑仓库市
     */
    private String city;
    /**
     * 逻辑仓库区
     */
    private String region;
    /**
     * 逻辑仓库地址
     */
    private String address;
    /**
     * 最后修改时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private String lastUpdateTimeStr;
    /**
     * 更新人
     */
    private String updateUserId;
}
