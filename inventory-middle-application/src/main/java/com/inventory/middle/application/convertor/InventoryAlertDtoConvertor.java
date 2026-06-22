package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.model.types.InventoryAlertId;
import com.inventory.middle.client.dto.InventoryAlertDto;
import com.inventory.middle.client.dto.command.InventoryAlertCommand;
import com.inventory.middle.infra.persistence.entity.InventoryAlertDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存报警日志DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
@Mapper(componentModel = "spring")
public interface InventoryAlertDtoConvertor {

    /**
     * dto --> entity
     * @param inventoryalertDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryAlert toInventoryAlert(final InventoryAlertDto inventoryalertDto);

    /**
     * dto --> entity
     * @param inventoryalertCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryAlert toInventoryAlert(final InventoryAlertCommand inventoryalertCommand);

    /**
     * entity --> dto
     * @param inventoryalert
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryAlertDto fromInventoryAlert(final InventoryAlert inventoryalert);

    /**
     * cmd --> dto
     * @param inventoryalertCommand
     * @return
     */
      InventoryAlertDto fromInventoryAlertCommand(final InventoryAlertCommand inventoryalertCommand);

    /**
     * do --> dto
     * @param inventoryalertDo
     * @return
     */
      InventoryAlertDto fromDo(final InventoryAlertDo inventoryalertDo);

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