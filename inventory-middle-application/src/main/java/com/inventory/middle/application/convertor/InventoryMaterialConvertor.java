package com.inventory.middle.application.convertor;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import com.inventory.middle.domain.model.entity.InventoryMaterial;
import com.inventory.middle.domain.repository.InventoryMaterialQueryParam;
import com.inventory.middle.domain.repository.ListMaterialCodeQueryParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMaterialConvertor {

    InventoryMaterial toEntity(InventoryMaterialCreateRequest request);

    InventoryMaterial toEntity(InventoryMaterialUpdateRequest request);

    ListMaterialCodeQueryParam toQueryParam(ListMaterialCodeRequest request);

    InventoryMaterialDTO toDTO(InventoryMaterial entity);

    InventoryMaterialQueryParam toQueryParam(InventoryMaterialPageRequest request);
}
