package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.InventoryPlan;
import com.inventory.middle.domain.model.types.InventoryPlanId;
import com.inventory.middle.client.dto.InventoryPlanDto;
import com.inventory.middle.client.dto.command.InventoryPlanCommand;
import com.inventory.middle.infra.persistence.entity.InventoryPlanDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-计划DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Mapper(componentModel = "spring")
public interface InventoryPlanDtoConvertor {

    /**
     * dto --> entity
     * @param inventoryplanDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    InventoryPlan toInventoryPlan(final InventoryPlanDto inventoryplanDto);

    /**
     * dto --> entity
     * @param inventoryplanCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryPlan toInventoryPlan(final InventoryPlanCommand inventoryplanCommand);

    /**
     * entity --> dto
     * @param inventoryplan
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     InventoryPlanDto fromInventoryPlan(final InventoryPlan inventoryplan);

    /**
     * cmd --> dto
     * @param inventoryplanCommand
     * @return
     */
      InventoryPlanDto fromInventoryPlanCommand(final InventoryPlanCommand inventoryplanCommand);

    /**
     * do --> dto
     * @param inventoryplanDo
     * @return
     */
      InventoryPlanDto fromDo(final InventoryPlanDo inventoryplanDo);

      default InventoryPlanId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new InventoryPlanId(id);
      }

      default Long id2id(InventoryPlanId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}