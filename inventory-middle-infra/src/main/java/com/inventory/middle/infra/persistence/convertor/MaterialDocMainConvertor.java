package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.model.types.MaterialDocMainId;
import com.inventory.middle.infra.persistence.entity.MaterialDocMainDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证主表Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Mapper(componentModel = "spring")
public interface MaterialDocMainConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MaterialDocMain toMaterialDocMain(MaterialDocMainDo materialdocmainDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MaterialDocMainDo fromMaterialDocMain(MaterialDocMain materialdocmain);

	default MaterialDocMainId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MaterialDocMainId(id);
	}

	default Long id2id(MaterialDocMainId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}