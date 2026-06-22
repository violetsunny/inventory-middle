package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 交运单明细ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
@Data
public class ShipmentLineId implements ValueObject<ShipmentLineId> {

	private final Long id;

	public ShipmentLineId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("交运单明细id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(ShipmentLineId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
