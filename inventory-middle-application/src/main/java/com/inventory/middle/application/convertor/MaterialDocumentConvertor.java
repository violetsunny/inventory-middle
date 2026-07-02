package com.inventory.middle.application.convertor;

import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialDocumentConvertor {

    MaterialDocumentBO toBO(MaterialDocumentDTO dto);
}
