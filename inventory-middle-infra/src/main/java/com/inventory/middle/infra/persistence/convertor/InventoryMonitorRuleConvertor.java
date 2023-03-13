package com.inventory.middle.infra.persistence.convertor;


import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Objects;

/**
 * 库存预警规则Convertor
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Mapper(componentModel = "spring")
public interface InventoryMonitorRuleConvertor {

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMonitorRule toInventoryMonitorRule(InventoryMonitorRuleDo inventorymonitorruleDo);

	@Mappings({
			@Mapping(source = "id", target = "id")
	})
	InventoryMonitorRuleDo fromInventoryMonitorRule(InventoryMonitorRule inventorymonitorrule);

	default InventoryMonitorRuleId id2id(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		return new InventoryMonitorRuleId(id);
	}

	default Long id2id(InventoryMonitorRuleId typeId) {
		if(Objects.isNull(typeId)) {
			return null;
		}
		return typeId.get();
	}

}