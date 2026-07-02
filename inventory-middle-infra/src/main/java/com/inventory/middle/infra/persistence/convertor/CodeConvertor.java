package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.Code;
import com.inventory.middle.domain.repository.CodeQueryParam;
import com.inventory.middle.infra.persistence.entity.CodeDo;
import com.inventory.middle.infra.persistence.entity.ListCodeParamPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeConvertor {

    CodeDo toDo(Code entity);

    Code toEntity(CodeDo doObj);

    ListCodeParamPO toListCodeParamPO(CodeQueryParam param);
}
