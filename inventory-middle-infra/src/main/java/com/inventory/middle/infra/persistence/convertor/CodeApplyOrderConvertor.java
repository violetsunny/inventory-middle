package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.CodeApplyOrder;
import com.inventory.middle.domain.repository.CodeApplyOrderQueryParam;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDo;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderParamPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeApplyOrderConvertor {

    CodeApplyOrderDo toDo(CodeApplyOrder entity);

    CodeApplyOrder toEntity(CodeApplyOrderDo doObj);

    CodeApplyOrderParamPO toParamPO(CodeApplyOrderQueryParam queryParam);
}
