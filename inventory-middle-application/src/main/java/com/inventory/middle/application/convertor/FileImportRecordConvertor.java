package com.inventory.middle.application.convertor;

import com.inventory.middle.client.file.dto.request.CreateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.request.UpdateFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import com.inventory.middle.domain.repository.FileImportRecordQueryParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileImportRecordConvertor {

    com.inventory.middle.domain.model.entity.FileImportRecord toEntity(CreateFileImportRecordRequest request);

    FileImportRecord toResponse(com.inventory.middle.domain.model.entity.FileImportRecord entity);

    com.inventory.middle.domain.model.entity.FileImportRecord toEntity(UpdateFileImportRecordRequest request);

    FileImportRecordQueryParam toQueryParam(PageQueryFileImportRecordRequest request);
}
