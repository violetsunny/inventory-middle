package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.model.types.InventoryDemandId;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.command.InventoryDemandCommand;
import com.inventory.middle.infra.persistence.entity.InventoryDemandDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-需求DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface InventoryDemandDtoConvertor {

    /**
     * dto --> entity
     * @param inventorydemandDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryDemand toInventoryDemand(final InventoryDemandDto inventorydemandDto);

    /**
     * dto --> entity
     * @param inventorydemandCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryDemand toInventoryDemand(final InventoryDemandCommand inventorydemandCommand);

    /**
     * entity --> dto
     * @param inventorydemand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryDemandDto fromInventoryDemand(final InventoryDemand inventorydemand);

    /**
     * cmd --> dto
     * @param inventorydemandCommand
     * @return
     */
      InventoryDemandDto fromInventoryDemandCommand(final InventoryDemandCommand inventorydemandCommand);

    /**
     * do --> dto
     * @param inventorydemandDo
     * @return
     */
      InventoryDemandDto fromDo(final InventoryDemandDo inventorydemandDo);

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