package com.inventory.middle.domain.model.types;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import top.kdla.framework.domain.shared.ValueObject;

/**
 * 批次号
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-10 09:55:21
 */
@Data
public class BatchNo implements ValueObject<BatchNo> {

	private final String batchNo;

	public BatchNo(final String batchNo) {
		if(StringUtils.isEmpty(batchNo)) {
			throw new IllegalArgumentException("批次号不能为空");
		}
		this.batchNo = batchNo;
	}

	public String get() {
		return batchNo;
	}

	@Override
	public boolean sameValueAs(BatchNo other) {
		return other != null && this.batchNo.equals(other.batchNo);
	}

	@Override
	public String toString() {
		return batchNo;
	}
}
