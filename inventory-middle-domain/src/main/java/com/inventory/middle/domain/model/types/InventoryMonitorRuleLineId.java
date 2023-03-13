package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 库存预警规则明细ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Data
public class InventoryMonitorRuleLineId implements ValueObject<InventoryMonitorRuleLineId> {

	private final Long id;

	public InventoryMonitorRuleLineId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("库存预警规则明细id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(InventoryMonitorRuleLineId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
