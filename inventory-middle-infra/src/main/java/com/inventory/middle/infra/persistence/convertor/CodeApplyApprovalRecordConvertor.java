package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.CodeApplyApprovalRecord;
import com.inventory.middle.infra.persistence.entity.CodeApplyApprovalRecordDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeApplyApprovalRecordConvertor {

    CodeApplyApprovalRecordDo toDo(CodeApplyApprovalRecord entity);

    CodeApplyApprovalRecord toEntity(CodeApplyApprovalRecordDo doObj);
}
