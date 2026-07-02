package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.CodeApplyOrderDetail;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDetailDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeApplyOrderDetailConvertor {

    CodeApplyOrderDetailDo toDo(CodeApplyOrderDetail entity);

    CodeApplyOrderDetail toEntity(CodeApplyOrderDetailDo doObj);
}
