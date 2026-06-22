package com.inventory.middle.domain.model.types;

import top.kdla.framework.domain.shared.ValueObject;
import java.util.Objects;

import lombok.Data;

/**
 * 物料凭证子表-物料信息ID
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Data
public class MdocSubMaterialId implements ValueObject<MdocSubMaterialId> {

	private final Long id;

	public MdocSubMaterialId(final Long id) {
		if(Objects.isNull(id)) {
			throw new IllegalArgumentException("物料凭证子表-物料信息id不能为空");
		}
		this.id = id;
	}

	public Long get() {
		return id;
	}

	@Override
	public boolean sameValueAs(MdocSubMaterialId other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
