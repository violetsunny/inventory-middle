package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MdocSubInOut;
import com.inventory.middle.domain.model.types.MdocSubInOutId;
import com.inventory.middle.infra.persistence.entity.MdocSubInOutDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证子表-出入库信息Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Mapper(componentModel = "spring")
public interface MdocSubInOutConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubInOut toMdocSubInOut(MdocSubInOutDo mdocsubinoutDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubInOutDo fromMdocSubInOut(MdocSubInOut mdocsubinout);

	default MdocSubInOutId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MdocSubInOutId(id);
	}

	default Long id2id(MdocSubInOutId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}