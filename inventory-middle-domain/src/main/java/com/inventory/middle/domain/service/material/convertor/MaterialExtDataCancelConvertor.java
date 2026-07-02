package com.inventory.middle.domain.service.material.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.material.MaterialExtDataDTO;
import com.inventory.middle.domain.model.bo.material.MaterialExtDataBO;

@Mapper(componentModel = "default")
public interface MaterialExtDataCancelConvertor {

    MaterialExtDataCancelConvertor INSTANCE = Mappers.getMapper(MaterialExtDataCancelConvertor.class);

    MaterialExtDataDTO toDTO(MaterialExtDataBO bo);
}
