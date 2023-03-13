package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.model.types.InventorySupplyId;
import com.inventory.middle.infra.persistence.entity.InventorySupplyDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存-供给Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Mapper(componentModel = "spring")
public interface InventorySupplyConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventorySupply toInventorySupply(InventorySupplyDo inventorysupplyDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventorySupplyDo fromInventorySupply(InventorySupply inventorysupply);

	default InventorySupplyId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventorySupplyId(id);
	}

	default Long id2id(InventorySupplyId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}