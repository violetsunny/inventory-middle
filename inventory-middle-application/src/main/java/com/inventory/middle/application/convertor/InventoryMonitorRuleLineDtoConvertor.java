package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleLineDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存预警规则明细DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface InventoryMonitorRuleLineDtoConvertor {

    /**
     * dto --> entity
     * @param inventorymonitorrulelineDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryMonitorRuleLine toInventoryMonitorRuleLine(final InventoryMonitorRuleLineDto inventorymonitorrulelineDto);

    /**
     * dto --> entity
     * @param inventorymonitorrulelineCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMonitorRuleLine toInventoryMonitorRuleLine(final InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * entity --> dto
     * @param inventorymonitorruleline
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMonitorRuleLineDto fromInventoryMonitorRuleLine(final InventoryMonitorRuleLine inventorymonitorruleline);

    /**
     * cmd --> dto
     * @param inventorymonitorrulelineCommand
     * @return
     */
      InventoryMonitorRuleLineDto fromInventoryMonitorRuleLineCommand(final InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * do --> dto
     * @param inventorymonitorrulelineDo
     * @return
     */
      InventoryMonitorRuleLineDto fromDo(final InventoryMonitorRuleLineDo inventorymonitorrulelineDo);

      default InventoryMonitorRuleLineId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new InventoryMonitorRuleLineId(id);
      }

      default Long id2id(InventoryMonitorRuleLineId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}