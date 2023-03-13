package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import com.inventory.middle.infra.persistence.entity.InventoryAlertNotificationDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存报警通知记录Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Mapper(componentModel = "spring")
public interface InventoryAlertNotificationConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryAlertNotification toInventoryAlertNotification(InventoryAlertNotificationDo inventoryalertnotificationDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryAlertNotificationDo fromInventoryAlertNotification(InventoryAlertNotification inventoryalertnotification);

	default InventoryAlertNotificationId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryAlertNotificationId(id);
	}

	default Long id2id(InventoryAlertNotificationId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}