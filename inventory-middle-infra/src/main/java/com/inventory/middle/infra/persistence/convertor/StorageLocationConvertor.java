package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.infra.persistence.entity.StorageLocationDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 存储地点表Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper(componentModel = "spring")
public interface StorageLocationConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	StorageLocation toStorageLocation(StorageLocationDo storagelocationDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	StorageLocationDo fromStorageLocation(StorageLocation storagelocation);

	default StorageLocationId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new StorageLocationId(id);
	}

	default Long id2id(StorageLocationId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}