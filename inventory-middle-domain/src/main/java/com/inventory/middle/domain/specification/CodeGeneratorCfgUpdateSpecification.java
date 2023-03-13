package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.CodeGeneratorCfg;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 流水号配置表Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class CodeGeneratorCfgUpdateSpecification extends AbstractSpecification<CodeGeneratorCfg> {


    public CodeGeneratorCfgUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(CodeGeneratorCfg codegeneratorcfg) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
