package com.inventory.middle.domain.service.material.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.material.MaterialDataDTO;
import com.inventory.middle.domain.model.bo.material.MaterialDataBO;

@Mapper(componentModel = "default")
public interface MaterialDataCancelConvertor {

    MaterialDataCancelConvertor INSTANCE = Mappers.getMapper(MaterialDataCancelConvertor.class);

    MaterialDataDTO toDTO(MaterialDataBO bo);
}
