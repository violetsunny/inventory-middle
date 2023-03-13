package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryTransit;
import com.inventory.middle.domain.model.types.InventoryTransitId;
import com.inventory.middle.infra.persistence.entity.InventoryTransitDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-在途Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Mapper(componentModel = "spring")
public interface InventoryTransitConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryTransit toInventoryTransit(InventoryTransitDo inventorytransitDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryTransitDo fromInventoryTransit(InventoryTransit inventorytransit);

	default InventoryTransitId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryTransitId(id);
	}

	default Long id2id(InventoryTransitId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}