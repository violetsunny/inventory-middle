package com.inventory.middle.domain.model.bo.logicalPlant;

import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分页查询逻辑仓返回BO
 * @author hjs
 */
@Data
public class PageListLogicalPlantResponseBO implements Serializable {

    private Long logicalPlantId;

    private String logicalPlantNo;

    private String logicalPlantName;

    private String companyCode;

    private Long warehouseId;

    /**
     * 物理仓库编码
     */
    private String warehouseNo;
    /**
     * 物理仓库名称
     */
    private String warehouseName;

    private String warehouseProvince;

    private String warehouseCity;

    private String warehouseRegion;

    private String warehouseAddress;

    private String creatorId;

    private List<StorageLocationBO> slList;

    private Integer slCount;

    private String province;

    private String city;

    private String region;

    private String address;

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
