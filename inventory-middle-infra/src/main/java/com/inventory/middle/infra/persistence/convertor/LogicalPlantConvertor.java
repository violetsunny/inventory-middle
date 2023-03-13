package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import com.inventory.middle.infra.persistence.entity.LogicalPlantDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 逻辑仓库表Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper(componentModel = "spring")
public interface LogicalPlantConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	LogicalPlant toLogicalPlant(LogicalPlantDo logicalplantDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	LogicalPlantDo fromLogicalPlant(LogicalPlant logicalplant);

	default LogicalPlantId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new LogicalPlantId(id);
	}

	default Long id2id(LogicalPlantId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}