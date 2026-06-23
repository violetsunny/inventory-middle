package com.inventory.middle.domain.service.map.converter;

import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.domain.service.map.bo.InventoryMapBO;

import java.util.Objects;

/**
 * 移动平均价 BO ↔ DTO 转换器
 * 迁移自: com.enn.inventory.center.biz.converter.map.MapBOConverter
 */
public class MapBizConverter {

    private MapBizConverter() {}

    /**
     * InventoryMapDTO → InventoryMapBO
     */
    public static InventoryMapBO toBO(InventoryMapDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        InventoryMapBO bo = new InventoryMapBO();
        bo.setMapCode(dto.getMapCode());
        bo.setMapSubCode(dto.getMapSubCode());
        bo.setSkuCode(dto.getSkuCode());
        bo.setLogicalPlantNo(dto.getLogicalPlantNo());
        bo.setMap(dto.getMap());
        return bo;
    }

    /**
     * InventoryMapBO → InventoryMapDTO
     */
    public static InventoryMapDTO toDTO(InventoryMapBO bo) {
        if (Objects.isNull(bo)) {
            return null;
        }
        InventoryMapDTO dto = new InventoryMapDTO();
        dto.setMapCode(bo.getMapCode());
        dto.setMapSubCode(bo.getMapSubCode());
        dto.setSkuCode(bo.getSkuCode());
        dto.setLogicalPlantNo(bo.getLogicalPlantNo());
        dto.setMap(bo.getMap());
        return dto;
    }
}
