package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 库存-需求ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Data
public class InventoryDemandId implements ValueObject<InventoryDemandId> {

	private final Long id;

	public InventoryDemandId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("库存-需求id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(InventoryDemandId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
