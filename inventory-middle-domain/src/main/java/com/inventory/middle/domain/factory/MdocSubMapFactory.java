package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.MdocSubMap;
import org.springframework.stereotype.Component;

/**
 * 物料凭证-标签-移动平均价Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Component
public class MdocSubMapFactory {

	public MdocSubMap createMdocSubMap() {
		return new MdocSubMap();
	}

}