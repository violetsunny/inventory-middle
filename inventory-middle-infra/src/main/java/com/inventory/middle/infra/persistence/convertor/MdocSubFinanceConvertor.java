package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.MdocSubFinance;
import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import com.inventory.middle.infra.persistence.entity.MdocSubFinanceDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 物料凭证-标签-财务Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Mapper(componentModel = "spring")
public interface MdocSubFinanceConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubFinance toMdocSubFinance(MdocSubFinanceDo mdocsubfinanceDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	MdocSubFinanceDo fromMdocSubFinance(MdocSubFinance mdocsubfinance);

	default MdocSubFinanceId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new MdocSubFinanceId(id);
	}

	default Long id2id(MdocSubFinanceId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}