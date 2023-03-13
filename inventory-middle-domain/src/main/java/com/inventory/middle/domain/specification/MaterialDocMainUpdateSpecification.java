package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MaterialDocMain;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证主表Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class MaterialDocMainUpdateSpecification extends AbstractSpecification<MaterialDocMain> {


    public MaterialDocMainUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MaterialDocMain materialdocmain) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
