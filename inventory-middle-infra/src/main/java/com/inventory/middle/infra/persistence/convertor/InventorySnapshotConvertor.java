package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.infra.persistence.entity.InventorySnapshotDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存快照-实时Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Mapper(componentModel = "spring")
public interface InventorySnapshotConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventorySnapshot toInventorySnapshot(InventorySnapshotDo inventorysnapshotDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventorySnapshotDo fromInventorySnapshot(InventorySnapshot inventorysnapshot);

	default InventorySnapshotId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventorySnapshotId(id);
	}

	default Long id2id(InventorySnapshotId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}