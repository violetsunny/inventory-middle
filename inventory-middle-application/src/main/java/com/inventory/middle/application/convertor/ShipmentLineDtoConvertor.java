package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.ShipmentLine;
import com.inventory.middle.domain.model.types.ShipmentLineId;
import com.inventory.middle.client.dto.ShipmentLineDto;
import com.inventory.middle.client.dto.command.ShipmentLineCommand;
import com.inventory.middle.infra.persistence.entity.ShipmentLineDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 交运单明细DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
@Mapper(componentModel = "spring")
public interface ShipmentLineDtoConvertor {

    /**
     * dto --> entity
     * @param shipmentlineDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    ShipmentLine toShipmentLine(final ShipmentLineDto shipmentlineDto);

    /**
     * dto --> entity
     * @param shipmentlineCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     ShipmentLine toShipmentLine(final ShipmentLineCommand shipmentlineCommand);

    /**
     * entity --> dto
     * @param shipmentline
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     ShipmentLineDto fromShipmentLine(final ShipmentLine shipmentline);

    /**
     * cmd --> dto
     * @param shipmentlineCommand
     * @return
     */
      ShipmentLineDto fromShipmentLineCommand(final ShipmentLineCommand shipmentlineCommand);

    /**
     * do --> dto
     * @param shipmentlineDo
     * @return
     */
      ShipmentLineDto fromDo(final ShipmentLineDo shipmentlineDo);

      default ShipmentLineId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new ShipmentLineId(id);
      }

      default Long id2id(ShipmentLineId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}