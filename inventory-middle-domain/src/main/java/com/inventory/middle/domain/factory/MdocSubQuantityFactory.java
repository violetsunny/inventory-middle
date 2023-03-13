package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import org.springframework.stereotype.Component;

/**
 * 物料凭证子表-数量Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class MdocSubQuantityFactory {

	public MdocSubQuantity createMdocSubQuantity() {
		return new MdocSubQuantity();
	}

}