package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 物料凭证-标签-移动平均价ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Data
public class MdocSubMapId implements ValueObject<MdocSubMapId> {

	private final Long id;

	public MdocSubMapId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("物料凭证-标签-移动平均价id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(MdocSubMapId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
