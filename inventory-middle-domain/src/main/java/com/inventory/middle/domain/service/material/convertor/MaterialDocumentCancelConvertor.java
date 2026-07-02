package com.inventory.middle.domain.service.material.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;

@Mapper(componentModel = "default")
public interface MaterialDocumentCancelConvertor {

    MaterialDocumentCancelConvertor INSTANCE = Mappers.getMapper(MaterialDocumentCancelConvertor.class);

    @Mapping(target = "materialDocumentItems", ignore = true)
    MaterialDocumentDTO toDTO(MaterialDocumentBO bo);
}
