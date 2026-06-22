package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.model.types.InventorySupplyId;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.command.InventorySupplyCommand;
import com.inventory.middle.infra.persistence.entity.InventorySupplyDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-供给DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface InventorySupplyDtoConvertor {

    /**
     * dto --> entity
     * @param inventorysupplyDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventorySupply toInventorySupply(final InventorySupplyDto inventorysupplyDto);

    /**
     * dto --> entity
     * @param inventorysupplyCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventorySupply toInventorySupply(final InventorySupplyCommand inventorysupplyCommand);

    /**
     * entity --> dto
     * @param inventorysupply
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventorySupplyDto fromInventorySupply(final InventorySupply inventorysupply);

    /**
     * cmd --> dto
     * @param inventorysupplyCommand
     * @return
     */
      InventorySupplyDto fromInventorySupplyCommand(final InventorySupplyCommand inventorysupplyCommand);

    /**
     * do --> dto
     * @param inventorysupplyDo
     * @return
     */
      InventorySupplyDto fromDo(final InventorySupplyDo inventorysupplyDo);

      default InventorySupplyId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new InventorySupplyId(id);
      }

      default Long id2id(InventorySupplyId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}