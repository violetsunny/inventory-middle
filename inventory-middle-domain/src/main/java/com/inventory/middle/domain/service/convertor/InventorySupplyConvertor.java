package com.inventory.middle.domain.service.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.domain.model.bo.inventory.InventorySupplyBO;
import com.inventory.middle.domain.model.entity.InventorySupply;

@Mapper(componentModel = "default")
public interface InventorySupplyConvertor {

    InventorySupplyConvertor INSTANCE = Mappers.getMapper(InventorySupplyConvertor.class);

    @Mapping(target = "id", ignore = true)
    InventorySupply toEntity(InventorySupplyBO bo);

    @Mapping(target = "id", ignore = true)
    InventorySupplyBO toBO(InventorySupply entity);
}
