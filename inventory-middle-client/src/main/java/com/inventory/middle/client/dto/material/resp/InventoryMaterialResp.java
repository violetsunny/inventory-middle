package com.inventory.middle.client.dto.material.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 库存物料DTO
 *
 * @author vincent.li
 * @date 2022/5/7 11:08
 */
@Data
public class InventoryMaterialResp implements Serializable {
    /**
     * 主键id
     */
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
     * 物料名称
     */
    private String materialName;

    /**
     * 外部物料编码
     */
    private String outMaterialCode;

    /**
     * 单位id
     */
    private Long unitId;

    /**
     * 单位信息
     */
    private UnitResp unit;
}
