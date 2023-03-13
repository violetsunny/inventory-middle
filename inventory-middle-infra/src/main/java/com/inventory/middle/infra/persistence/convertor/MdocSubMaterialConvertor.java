package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import com.inventory.middle.domain.model.types.MdocSubMaterialId;
import com.inventory.middle.infra.persistence.entity.MdocSubMaterialDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证子表-物料信息Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Mapper(componentModel = "spring")
public interface MdocSubMaterialConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubMaterial toMdocSubMaterial(MdocSubMaterialDo mdocsubmaterialDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubMaterialDo fromMdocSubMaterial(MdocSubMaterial mdocsubmaterial);

	default MdocSubMaterialId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MdocSubMaterialId(id);
	}

	default Long id2id(MdocSubMaterialId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}