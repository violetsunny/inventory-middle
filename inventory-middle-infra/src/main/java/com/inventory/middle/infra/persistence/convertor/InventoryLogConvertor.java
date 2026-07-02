package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.InventoryLog;
import com.inventory.middle.infra.persistence.entity.InventoryLogDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryLogConvertor {

    InventoryLogDo toDo(InventoryLog entity);

    InventoryLog toEntity(InventoryLogDo doObj);
}
