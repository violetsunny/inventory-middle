package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.model.types.InventoryDemandId;
import com.inventory.middle.infra.persistence.entity.InventoryDemandDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-需求Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:24
 */
@Mapper(componentModel = "spring")
public interface InventoryDemandConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryDemand toInventoryDemand(InventoryDemandDo inventorydemandDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryDemandDo fromInventoryDemand(InventoryDemand inventorydemand);

	default InventoryDemandId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryDemandId(id);
	}

	default Long id2id(InventoryDemandId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}