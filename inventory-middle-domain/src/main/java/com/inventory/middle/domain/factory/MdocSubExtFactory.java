package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MdocSubExt;
import org.springframework.stereotype.Component;

/**
 * 物料凭证-标签-扩展Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Component
public class MdocSubExtFactory {

	public MdocSubExt createMdocSubExt() {
		return new MdocSubExt();
	}

}