package com.inventory.middle.domain.service.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.entity.LogicalPlant;

@Mapper(componentModel = "default")
public interface LogicalPlantConvertor {

    LogicalPlantConvertor INSTANCE = Mappers.getMapper(LogicalPlantConvertor.class);

    @Mapping(target = "id", ignore = true)
    LogicalPlantBO toBO(LogicalPlant entity);
}
