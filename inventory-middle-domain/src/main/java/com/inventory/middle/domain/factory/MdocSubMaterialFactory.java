package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import org.springframework.stereotype.Component;

/**
 * 物料凭证子表-物料信息Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class MdocSubMaterialFactory {

	public MdocSubMaterial createMdocSubMaterial() {
		return new MdocSubMaterial();
	}

}