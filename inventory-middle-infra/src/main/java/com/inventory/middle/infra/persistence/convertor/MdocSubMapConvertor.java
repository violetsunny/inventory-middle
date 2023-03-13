package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MdocSubMap;
import com.inventory.middle.domain.model.types.MdocSubMapId;
import com.inventory.middle.infra.persistence.entity.MdocSubMapDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-标签-移动平均价Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Mapper(componentModel = "spring")
public interface MdocSubMapConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubMap toMdocSubMap(MdocSubMapDo mdocsubmapDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubMapDo fromMdocSubMap(MdocSubMap mdocsubmap);

	default MdocSubMapId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MdocSubMapId(id);
	}

	default Long id2id(MdocSubMapId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}