package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MdocSubInOut;
import org.springframework.stereotype.Component;

/**
 * 物料凭证子表-出入库信息Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class MdocSubInOutFactory {

	public MdocSubInOut createMdocSubInOut() {
		return new MdocSubInOut();
	}

}