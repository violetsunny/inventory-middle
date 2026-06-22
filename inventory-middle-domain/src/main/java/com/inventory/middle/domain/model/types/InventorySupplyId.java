package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 库存-供给ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Data
public class InventorySupplyId implements ValueObject<InventorySupplyId> {

	private final Long id;

	public InventorySupplyId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("库存-供给id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(InventorySupplyId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
