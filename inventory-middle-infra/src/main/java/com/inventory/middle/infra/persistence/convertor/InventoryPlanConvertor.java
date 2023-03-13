package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryPlan;
import com.inventory.middle.domain.model.types.InventoryPlanId;
import com.inventory.middle.infra.persistence.entity.InventoryPlanDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-计划Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Mapper(componentModel = "spring")
public interface InventoryPlanConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryPlan toInventoryPlan(InventoryPlanDo inventoryplanDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryPlanDo fromInventoryPlan(InventoryPlan inventoryplan);

	default InventoryPlanId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryPlanId(id);
	}

	default Long id2id(InventoryPlanId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}