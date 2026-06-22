package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.CodeGeneratorCfg;
import com.inventory.middle.domain.model.types.CodeGeneratorCfgId;
import com.inventory.middle.infra.persistence.entity.CodeGeneratorCfgDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 流水号配置表Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper(componentModel = "spring")
public interface CodeGeneratorCfgConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	CodeGeneratorCfg toCodeGeneratorCfg(CodeGeneratorCfgDo codegeneratorcfgDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	CodeGeneratorCfgDo fromCodeGeneratorCfg(CodeGeneratorCfg codegeneratorcfg);

	default CodeGeneratorCfgId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new CodeGeneratorCfgId(id);
	}

	default Long id2id(CodeGeneratorCfgId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}