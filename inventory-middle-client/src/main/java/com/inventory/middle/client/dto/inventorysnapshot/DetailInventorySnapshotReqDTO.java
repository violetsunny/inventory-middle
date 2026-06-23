package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description 库存
 * @author peisheng.wang
 * @date 2021-06-16
 */
@Data
public class DetailInventorySnapshotReqDTO extends BaseRequest {

    /**
     * 物料code
     */
    private String materialCode;

    /**
     * 逻辑仓ID
     */
    private Long logicalPlantId;


    /**
     * 存储地点ID
     */
    private Long locationId;


    /**
     * 详情查询粒度 0-逻辑仓 1-存储地点 2-批次
     */
    private Integer type;

    
    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;



}
