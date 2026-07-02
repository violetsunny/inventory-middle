package com.inventory.middle.domain.service.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.transit.CreateInTransitStockRequest;
import com.inventory.middle.domain.model.entity.InventorySupply;

@Mapper(componentModel = "default")
public interface CreateInTransitStockRequestConvertor {

    CreateInTransitStockRequestConvertor INSTANCE = Mappers.getMapper(CreateInTransitStockRequestConvertor.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "batchNo", ignore = true)
    @Mapping(target = "batchTime", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "logicalPlantNo", source = "logicalPlantId",
            defaultExpression = "java(String.valueOf(request.getLogicalPlantId()))")
    @Mapping(target = "storageLocationNo", source = "locationId",
            defaultExpression = "java(request.getLocationId() == null ? null : String.valueOf(request.getLocationId()))")
    @Mapping(target = "damaged", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "availableDate", ignore = true)
    @Mapping(target = "supplyType", source = "sourceOrderType")
    @Mapping(target = "unrestricted", source = "quantity")
    @Mapping(target = "productDate", source = "eta")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updatorId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    InventorySupply toEntity(CreateInTransitStockRequest request);
}
