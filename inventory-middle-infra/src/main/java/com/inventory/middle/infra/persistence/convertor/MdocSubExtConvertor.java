package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MdocSubExt;
import com.inventory.middle.domain.model.types.MdocSubExtId;
import com.inventory.middle.infra.persistence.entity.MdocSubExtDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-标签-扩展Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Mapper(componentModel = "spring")
public interface MdocSubExtConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubExt toMdocSubExt(MdocSubExtDo mdocsubextDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubExtDo fromMdocSubExt(MdocSubExt mdocsubext);

	default MdocSubExtId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MdocSubExtId(id);
	}

	default Long id2id(MdocSubExtId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}