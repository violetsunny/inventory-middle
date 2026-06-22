package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.model.types.InventoryMapHisId;
import com.inventory.middle.client.dto.InventoryMapHisDto;
import com.inventory.middle.client.dto.command.InventoryMapHisCommand;
import com.inventory.middle.infra.persistence.entity.InventoryMapHisDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 移动平均价历史记录DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface InventoryMapHisDtoConvertor {

    /**
     * dto --> entity
     * @param inventorymaphisDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryMapHis toInventoryMapHis(final InventoryMapHisDto inventorymaphisDto);

    /**
     * dto --> entity
     * @param inventorymaphisCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMapHis toInventoryMapHis(final InventoryMapHisCommand inventorymaphisCommand);

    /**
     * entity --> dto
     * @param inventorymaphis
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMapHisDto fromInventoryMapHis(final InventoryMapHis inventorymaphis);

    /**
     * cmd --> dto
     * @param inventorymaphisCommand
     * @return
     */
      InventoryMapHisDto fromInventoryMapHisCommand(final InventoryMapHisCommand inventorymaphisCommand);

    /**
     * do --> dto
     * @param inventorymaphisDo
     * @return
     */
      InventoryMapHisDto fromDo(final InventoryMapHisDo inventorymaphisDo);

      default InventoryMapHisId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new InventoryMapHisId(id);
      }

      default Long id2id(InventoryMapHisId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}