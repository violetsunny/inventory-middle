package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MaterialDocMain;
import org.springframework.stereotype.Component;

/**
 * 物料凭证主表Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class MaterialDocMainFactory {

	public MaterialDocMain createMaterialDocMain() {
		return new MaterialDocMain();
	}

}