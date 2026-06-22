package com.inventory.middle.domain.model.types;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import top.kdla.framework.domain.shared.ValueObject;

/**
 * 物料编码
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-10 09:55:21
 */
@Data
public class MaterialCode implements ValueObject<MaterialCode> {

	private final String materialCode;

	public MaterialCode(final String materialCode) {
		if(StringUtils.isEmpty(materialCode)) {
			throw new IllegalArgumentException("物料编码不能为空");
		}
		this.materialCode = materialCode;
	}

	public String get() {
		return materialCode;
	}

	@Override
	public boolean sameValueAs(MaterialCode other) {
		return other != null && this.materialCode.equals(other.materialCode);
	}

	@Override
	public String toString() {
		return materialCode;
	}
}
