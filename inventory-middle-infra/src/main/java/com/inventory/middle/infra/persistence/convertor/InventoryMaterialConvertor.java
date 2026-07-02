package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.model.entity.InventoryMaterial;
import com.inventory.middle.domain.repository.InventoryMaterialQueryParam;
import com.inventory.middle.domain.repository.ListMaterialCodeQueryParam;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialDo;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialQueryPO;
import com.inventory.middle.infra.persistence.entity.ListMaterialCodeParamPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMaterialConvertor {

    InventoryMaterialDo toDo(InventoryMaterial entity);

    InventoryMaterial toEntity(InventoryMaterialDo doObj);

    InventoryMaterialQueryPO toQueryPO(InventoryMaterialQueryParam queryParam);

    ListMaterialCodeParamPO toListMaterialCodeParamPO(ListMaterialCodeQueryParam queryParam);
}
