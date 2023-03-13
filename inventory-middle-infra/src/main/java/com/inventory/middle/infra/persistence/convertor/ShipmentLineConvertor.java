package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.ShipmentLine;
import com.inventory.middle.domain.model.types.ShipmentLineId;
import com.inventory.middle.infra.persistence.entity.ShipmentLineDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 交运单明细Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper(componentModel = "spring")
public interface ShipmentLineConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	ShipmentLine toShipmentLine(ShipmentLineDo shipmentlineDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	ShipmentLineDo fromShipmentLine(ShipmentLine shipmentline);

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