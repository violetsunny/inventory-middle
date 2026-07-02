package com.inventory.middle.domain.service.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.domain.model.bo.storageLocation.StorageLocationBO;
import com.inventory.middle.domain.model.entity.StorageLocation;

@Mapper(componentModel = "default")
public interface StorageLocationConvertor {

    StorageLocationConvertor INSTANCE = Mappers.getMapper(StorageLocationConvertor.class);

    @Mapping(target = "id", ignore = true)
    StorageLocationBO toBO(StorageLocation entity);
}
