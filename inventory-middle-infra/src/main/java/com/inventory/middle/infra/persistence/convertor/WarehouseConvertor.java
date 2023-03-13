package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.Warehouse;
import com.inventory.middle.domain.model.types.WarehouseId;
import com.inventory.middle.infra.persistence.entity.WarehouseDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物理仓库表Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper(componentModel = "spring")
public interface WarehouseConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	Warehouse toWarehouse(WarehouseDo warehouseDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	WarehouseDo fromWarehouse(Warehouse warehouse);

	default WarehouseId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new WarehouseId(id);
	}

	default Long id2id(WarehouseId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}