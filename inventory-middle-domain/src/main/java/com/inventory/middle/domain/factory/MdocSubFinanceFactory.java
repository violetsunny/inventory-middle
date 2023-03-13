package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MdocSubFinance;
import org.springframework.stereotype.Component;

/**
 * 物料凭证-标签-财务Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class MdocSubFinanceFactory {

	public MdocSubFinance createMdocSubFinance() {
		return new MdocSubFinance();
	}

}