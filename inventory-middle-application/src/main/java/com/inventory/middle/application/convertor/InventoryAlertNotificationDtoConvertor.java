package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import com.inventory.middle.client.dto.InventoryAlertNotificationDto;
import com.inventory.middle.client.dto.command.InventoryAlertNotificationCommand;
import com.inventory.middle.infra.persistence.entity.InventoryAlertNotificationDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存报警通知记录DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface InventoryAlertNotificationDtoConvertor {

    /**
     * dto --> entity
     * @param inventoryalertnotificationDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryAlertNotification toInventoryAlertNotification(final InventoryAlertNotificationDto inventoryalertnotificationDto);

    /**
     * dto --> entity
     * @param inventoryalertnotificationCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryAlertNotification toInventoryAlertNotification(final InventoryAlertNotificationCommand inventoryalertnotificationCommand);

    /**
     * entity --> dto
     * @param inventoryalertnotification
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryAlertNotificationDto fromInventoryAlertNotification(final InventoryAlertNotification inventoryalertnotification);

    /**
     * cmd --> dto
     * @param inventoryalertnotificationCommand
     * @return
     */
      InventoryAlertNotificationDto fromInventoryAlertNotificationCommand(final InventoryAlertNotificationCommand inventoryalertnotificationCommand);

    /**
     * do --> dto
     * @param inventoryalertnotificationDo
     * @return
     */
      InventoryAlertNotificationDto fromDo(final InventoryAlertNotificationDo inventoryalertnotificationDo);

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