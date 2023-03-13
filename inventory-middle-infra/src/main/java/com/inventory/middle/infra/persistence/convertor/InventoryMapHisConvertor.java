package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.model.types.InventoryMapHisId;
import com.inventory.middle.infra.persistence.entity.InventoryMapHisDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 移动平均价历史记录Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper(componentModel = "spring")
public interface InventoryMapHisConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMapHis toInventoryMapHis(InventoryMapHisDo inventorymaphisDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMapHisDo fromInventoryMapHis(InventoryMapHis inventorymaphis);

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