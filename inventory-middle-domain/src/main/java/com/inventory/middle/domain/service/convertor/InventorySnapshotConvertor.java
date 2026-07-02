package com.inventory.middle.domain.service.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.entity.InventorySnapshot;

@Mapper(componentModel = "default")
public interface InventorySnapshotConvertor {

    InventorySnapshotConvertor INSTANCE = Mappers.getMapper(InventorySnapshotConvertor.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", expression = "java(entity.getDeleted() != null && entity.getDeleted() == 1)")
    InventorySnapshotBO toBO(InventorySnapshot entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", expression = "java(bo.getDeleted() != null && bo.getDeleted() ? 1 : 0)")
    InventorySnapshot toEntity(InventorySnapshotBO bo);
}
