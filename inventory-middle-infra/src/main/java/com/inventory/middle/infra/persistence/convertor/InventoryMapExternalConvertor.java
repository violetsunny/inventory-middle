package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.domain.model.entity.InventoryMap;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapExternalConvertor {

    InventoryMapDTO toDTO(InventoryMap entity);
}
