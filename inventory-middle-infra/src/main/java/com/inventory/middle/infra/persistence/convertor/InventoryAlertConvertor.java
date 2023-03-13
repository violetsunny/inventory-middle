package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.model.types.InventoryAlertId;
import com.inventory.middle.infra.persistence.entity.InventoryAlertDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存报警日志Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Mapper(componentModel = "spring")
public interface InventoryAlertConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryAlert toInventoryAlert(InventoryAlertDo inventoryalertDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryAlertDo fromInventoryAlert(InventoryAlert inventoryalert);

	default InventoryAlertId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryAlertId(id);
	}

	default Long id2id(InventoryAlertId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}