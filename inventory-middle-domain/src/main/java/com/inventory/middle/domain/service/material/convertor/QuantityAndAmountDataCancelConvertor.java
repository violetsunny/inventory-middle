package com.inventory.middle.domain.service.material.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.material.QuantityAndAmountDataDTO;
import com.inventory.middle.domain.model.bo.material.QuantityAndAmountDataBO;

@Mapper(componentModel = "default")
public interface QuantityAndAmountDataCancelConvertor {

    QuantityAndAmountDataCancelConvertor INSTANCE = Mappers.getMapper(QuantityAndAmountDataCancelConvertor.class);

    QuantityAndAmountDataDTO toDTO(QuantityAndAmountDataBO bo);
}
