package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 库存快照-实时ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Data
public class InventorySnapshotId implements ValueObject<InventorySnapshotId> {

	private final Long id;

	public InventorySnapshotId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("库存快照-实时id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(InventorySnapshotId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
