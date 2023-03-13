package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.Shipment;
import com.inventory.middle.domain.model.types.ShipmentId;
import com.inventory.middle.infra.persistence.entity.ShipmentDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 交运单Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:25
 */
@Mapper(componentModel = "spring")
public interface ShipmentConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	Shipment toShipment(ShipmentDo shipmentDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	ShipmentDo fromShipment(Shipment shipment);

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