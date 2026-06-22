package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.types.InventoryMapId;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.command.InventoryMapCommand;
import com.inventory.middle.infra.persistence.entity.InventoryMapDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 移动平均价DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface InventoryMapDtoConvertor {

    /**
     * dto --> entity
     * @param inventorymapDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryMap toInventoryMap(final InventoryMapDto inventorymapDto);

    /**
     * dto --> entity
     * @param inventorymapCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMap toInventoryMap(final InventoryMapCommand inventorymapCommand);

    /**
     * entity --> dto
     * @param inventorymap
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMapDto fromInventoryMap(final InventoryMap inventorymap);

    /**
     * cmd --> dto
     * @param inventorymapCommand
     * @return
     */
      InventoryMapDto fromInventoryMapCommand(final InventoryMapCommand inventorymapCommand);

    /**
     * do --> dto
     * @param inventorymapDo
     * @return
     */
      InventoryMapDto fromDo(final InventoryMapDo inventorymapDo);

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