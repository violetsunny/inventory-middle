package com.inventory.middle.domain.service.material.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inventory.middle.client.dto.material.FinancialDataDTO;
import com.inventory.middle.domain.model.bo.material.FinancialDataBO;

@Mapper(componentModel = "default")
public interface FinancialDataCancelConvertor {

    FinancialDataCancelConvertor INSTANCE = Mappers.getMapper(FinancialDataCancelConvertor.class);

    FinancialDataDTO toDTO(FinancialDataBO bo);
}
