package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MaterialDocItem;
import org.springframework.stereotype.Component;

/**
 * 物料凭证-itemFactory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class MaterialDocItemFactory {

	public MaterialDocItem createMaterialDocItem() {
		return new MaterialDocItem();
	}

}