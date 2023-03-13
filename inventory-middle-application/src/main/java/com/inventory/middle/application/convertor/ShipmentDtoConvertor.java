package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.model.entity.Shipment;
import com.inventory.middle.domain.model.types.ShipmentId;
import com.inventory.middle.client.dto.ShipmentDto;
import com.inventory.middle.client.dto.command.ShipmentCommand;
import com.inventory.middle.infra.persistence.entity.ShipmentDo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 交运单DtoConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Mapper(componentModel = "spring")
public interface ShipmentDtoConvertor {

    /**
     * dto --> entity
     * @param shipmentDto
     * @return
     */
    @Mappings({@Mapping(source = "id", target = "id")})
    Shipment toShipment(final ShipmentDto shipmentDto);

    /**
     * dto --> entity
     * @param shipmentCommand
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     Shipment toShipment(final ShipmentCommand shipmentCommand);

    /**
     * entity --> dto
     * @param shipment
     * @return
     */
     @Mappings({@Mapping(source = "id", target = "id")})
     ShipmentDto fromShipment(final Shipment shipment);

    /**
     * cmd --> dto
     * @param shipmentCommand
     * @return
     */
      ShipmentDto fromShipmentCommand(final ShipmentCommand shipmentCommand);

    /**
     * do --> dto
     * @param shipmentDo
     * @return
     */
      ShipmentDto fromDo(final ShipmentDo shipmentDo);

      default ShipmentId id2id(Long id) {
          if(Objects.isNull(id)) {
             return null;
         }
          return new ShipmentId(id);
      }

      default Long id2id(ShipmentId typeId) {
          if(Objects.isNull(typeId)) {
             return null;
         }
         return typeId.get();
      }

}