package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MaterialDocItem;
import com.inventory.middle.domain.model.types.MaterialDocItemId;
import com.inventory.middle.infra.persistence.entity.MaterialDocItemDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-itemConvertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Mapper(componentModel = "spring")
public interface MaterialDocItemConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MaterialDocItem toMaterialDocItem(MaterialDocItemDo materialdocitemDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MaterialDocItemDo fromMaterialDocItem(MaterialDocItem materialdocitem);

	default MaterialDocItemId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MaterialDocItemId(id);
	}

	default Long id2id(MaterialDocItemId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}