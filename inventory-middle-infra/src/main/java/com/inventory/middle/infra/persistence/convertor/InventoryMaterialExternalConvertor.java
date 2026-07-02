package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMaterialExternalConvertor {

    InventoryMaterialDTO toDTO(InventoryMaterialDo doObj);
}
