package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.CodeGeneratorCfg;
import org.springframework.stereotype.Component;

/**
 * 流水号配置表Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class CodeGeneratorCfgFactory {

	public CodeGeneratorCfg createCodeGeneratorCfg() {
		return new CodeGeneratorCfg();
	}

}