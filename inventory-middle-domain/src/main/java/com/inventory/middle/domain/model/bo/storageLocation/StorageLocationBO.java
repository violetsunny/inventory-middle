package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 存储地点 BO（domain 层使用，对应 infra.StorageLocationDo） */
@Data
public class StorageLocationBO implements Serializable {
    private Long id;
    private String tenantId;
    private String storageLocationNo;
    private String storageLocationName;
    private Long logicalPlantNo;
    private Integer locationType;
    private String description;
    private String position;
    private String creatorId;
    private String updatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
