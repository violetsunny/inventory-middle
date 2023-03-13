package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存预警规则DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Mapper(componentModel = "spring")
public interface InventoryMonitorRuleDtoConvertor {

    /**
     * dto --> entity
     * @param inventorymonitorruleDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryMonitorRule toInventoryMonitorRule(final InventoryMonitorRuleDto inventorymonitorruleDto);

    /**
     * dto --> entity
     * @param inventorymonitorruleCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMonitorRule toInventoryMonitorRule(final InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * entity --> dto
     * @param inventorymonitorrule
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryMonitorRuleDto fromInventoryMonitorRule(final InventoryMonitorRule inventorymonitorrule);

    /**
     * cmd --> dto
     * @param inventorymonitorruleCommand
     * @return
     */
      InventoryMonitorRuleDto fromInventoryMonitorRuleCommand(final InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * do --> dto
     * @param inventorymonitorruleDo
     * @return
     */
      InventoryMonitorRuleDto fromDo(final InventoryMonitorRuleDo inventorymonitorruleDo);

      default InventoryMonitorRuleId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new InventoryMonitorRuleId(id);
      }

      default Long id2id(InventoryMonitorRuleId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}