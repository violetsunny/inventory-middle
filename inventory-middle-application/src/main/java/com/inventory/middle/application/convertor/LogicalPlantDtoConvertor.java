package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.command.LogicalPlantCommand;
import com.inventory.middle.infra.persistence.entity.LogicalPlantDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 逻辑仓库表DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface LogicalPlantDtoConvertor {

    /**
     * dto --> entity
     * @param logicalplantDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    LogicalPlant toLogicalPlant(final LogicalPlantDto logicalplantDto);

    /**
     * dto --> entity
     * @param logicalplantCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     LogicalPlant toLogicalPlant(final LogicalPlantCommand logicalplantCommand);

    /**
     * entity --> dto
     * @param logicalplant
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     LogicalPlantDto fromLogicalPlant(final LogicalPlant logicalplant);

    /**
     * cmd --> dto
     * @param logicalplantCommand
     * @return
     */
      LogicalPlantDto fromLogicalPlantCommand(final LogicalPlantCommand logicalplantCommand);

    /**
     * do --> dto
     * @param logicalplantDo
     * @return
     */
      LogicalPlantDto fromDo(final LogicalPlantDo logicalplantDo);

      default LogicalPlantId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new LogicalPlantId(id);
      }

      default Long id2id(LogicalPlantId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}