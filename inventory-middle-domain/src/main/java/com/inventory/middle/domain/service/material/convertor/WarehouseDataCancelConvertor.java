package com.inventory.middle.domain.service.material.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.material.WarehouseDataDTO;
import com.inventory.middle.domain.model.bo.material.WarehouseDataBO;

@Mapper(componentModel = "default")
public interface WarehouseDataCancelConvertor {

    WarehouseDataCancelConvertor INSTANCE = Mappers.getMapper(WarehouseDataCancelConvertor.class);

    WarehouseDataDTO toDTO(WarehouseDataBO bo);
}
