package com.inventory.middle.application.convertor;

import com.inventory.middle.client.code.dto.CodeApplyOrderDTO;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderCreateRequest;
import com.inventory.middle.client.code.dto.request.CodeApplyOrderPageRequest;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderCreateResponse;
import com.inventory.middle.client.code.dto.response.CodeApplyOrderInfoResponse;
import com.inventory.middle.domain.model.entity.CodeApplyOrder;
import com.inventory.middle.domain.repository.CodeApplyOrderQueryParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeApplyOrderConvertor {

    CodeApplyOrder toEntity(CodeApplyOrderCreateRequest request);

    CodeApplyOrderCreateResponse toCreateResponse(CodeApplyOrder entity);

    CodeApplyOrderInfoResponse toInfoResponse(CodeApplyOrder entity);

    CodeApplyOrderQueryParam toQueryParam(CodeApplyOrderPageRequest request);

    CodeApplyOrderDTO toDTO(CodeApplyOrder entity);
}
