package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.MaterialLogicalPlantRef;
import com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialLogicalPlantRefConvertor {

    MaterialLogicalPlantRefDo toDo(MaterialLogicalPlantRef entity);

    MaterialLogicalPlantRef toEntity(MaterialLogicalPlantRefDo doObj);
}
