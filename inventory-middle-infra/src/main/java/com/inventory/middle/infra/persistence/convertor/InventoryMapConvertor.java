package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.types.InventoryMapId;
import com.inventory.middle.infra.persistence.entity.InventoryMapDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 移动平均价Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:25
 */
@Mapper(componentModel = "spring")
public interface InventoryMapConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMap toInventoryMap(InventoryMapDo inventorymapDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMapDo fromInventoryMap(InventoryMap inventorymap);

	default InventoryMapId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryMapId(id);
	}

	default Long id2id(InventoryMapId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}