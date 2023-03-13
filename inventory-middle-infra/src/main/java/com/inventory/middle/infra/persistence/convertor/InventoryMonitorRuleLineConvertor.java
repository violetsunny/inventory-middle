package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleLineDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存预警规则明细Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Mapper(componentModel = "spring")
public interface InventoryMonitorRuleLineConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMonitorRuleLine toInventoryMonitorRuleLine(InventoryMonitorRuleLineDo inventorymonitorrulelineDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMonitorRuleLineDo fromInventoryMonitorRuleLine(InventoryMonitorRuleLine inventorymonitorruleline);

	default InventoryMonitorRuleLineId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryMonitorRuleLineId(id);
	}

	default Long id2id(InventoryMonitorRuleLineId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}