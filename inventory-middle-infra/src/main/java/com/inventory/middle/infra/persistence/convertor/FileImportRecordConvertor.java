package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.FileImportRecord;
import com.inventory.middle.domain.repository.FileImportRecordQueryParam;
import com.inventory.middle.infra.persistence.entity.FileImportRecordDo;
import com.inventory.middle.infra.persistence.entity.ListFileImportRecordParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileImportRecordConvertor {

    FileImportRecordDo toDo(FileImportRecord entity);

    FileImportRecord toEntity(FileImportRecordDo doObj);

    ListFileImportRecordParam toParam(FileImportRecordQueryParam queryParam);
}
