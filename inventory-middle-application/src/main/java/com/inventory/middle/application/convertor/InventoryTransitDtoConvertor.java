package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryTransit;
import com.inventory.middle.domain.model.types.InventoryTransitId;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.command.InventoryTransitCommand;
import com.inventory.middle.infra.persistence.entity.InventoryTransitDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-在途DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface InventoryTransitDtoConvertor {

    /**
     * dto --> entity
     * @param inventorytransitDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryTransit toInventoryTransit(final InventoryTransitDto inventorytransitDto);

    /**
     * dto --> entity
     * @param inventorytransitCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryTransit toInventoryTransit(final InventoryTransitCommand inventorytransitCommand);

    /**
     * entity --> dto
     * @param inventorytransit
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryTransitDto fromInventoryTransit(final InventoryTransit inventorytransit);

    /**
     * cmd --> dto
     * @param inventorytransitCommand
     * @return
     */
      InventoryTransitDto fromInventoryTransitCommand(final InventoryTransitCommand inventorytransitCommand);

    /**
     * do --> dto
     * @param inventorytransitDo
     * @return
     */
      InventoryTransitDto fromDo(final InventoryTransitDo inventorytransitDo);

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