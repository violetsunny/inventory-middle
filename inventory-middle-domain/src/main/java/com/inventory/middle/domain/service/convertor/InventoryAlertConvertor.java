package com.inventory.middle.domain.service.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.domain.model.bo.monitor.InventoryAlertBO;
import com.inventory.middle.domain.model.entity.InventoryAlert;

@Mapper(componentModel = "default")
public interface InventoryAlertConvertor {

    InventoryAlertConvertor INSTANCE = Mappers.getMapper(InventoryAlertConvertor.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updatorId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "logicalPlantNo", ignore = true)
    InventoryAlert toEntity(InventoryAlertBO bo);
}
