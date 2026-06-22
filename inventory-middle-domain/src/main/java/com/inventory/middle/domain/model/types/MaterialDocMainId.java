package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 物料凭证主表ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Data
public class MaterialDocMainId implements ValueObject<MaterialDocMainId> {

	private final Long id;

	public MaterialDocMainId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("物料凭证主表id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(MaterialDocMainId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
