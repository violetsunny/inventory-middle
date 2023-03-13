package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 移动平均价历史记录ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Data
public class InventoryMapHisId implements ValueObject<InventoryMapHisId> {

	private final Long id;

	public InventoryMapHisId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("移动平均价历史记录id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(InventoryMapHisId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
