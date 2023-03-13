package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import com.inventory.middle.domain.model.types.MdocSubQuantityId;
import com.inventory.middle.infra.persistence.entity.MdocSubQuantityDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证子表-数量Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Mapper(componentModel = "spring")
public interface MdocSubQuantityConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubQuantity toMdocSubQuantity(MdocSubQuantityDo mdocsubquantityDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubQuantityDo fromMdocSubQuantity(MdocSubQuantity mdocsubquantity);

	default MdocSubQuantityId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MdocSubQuantityId(id);
	}

	default Long id2id(MdocSubQuantityId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}