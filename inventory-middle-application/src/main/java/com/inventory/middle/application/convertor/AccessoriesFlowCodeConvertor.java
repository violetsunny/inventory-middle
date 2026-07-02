package com.inventory.middle.application.convertor;

import com.inventory.middle.client.code.dto.request.ListAccessoriesFlowCodeRequest;
import com.inventory.middle.client.code.dto.request.ListUnUsedAccessoriesFlowCodeRequest;
import com.inventory.middle.client.code.dto.response.AccessoriesFlowCodeResponse;
import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.domain.repository.CodeQueryParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessoriesFlowCodeConvertor {

    AccessoriesFlowCodeResponse toResponse(Code entity);

    CodeQueryParam toQueryParam(ListAccessoriesFlowCodeRequest request);

    CodeQueryParam toQueryParam(ListUnUsedAccessoriesFlowCodeRequest request);
}
