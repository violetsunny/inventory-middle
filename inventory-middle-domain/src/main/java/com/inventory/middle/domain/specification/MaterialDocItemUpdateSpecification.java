package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MaterialDocItem;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证-itemSpecification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class MaterialDocItemUpdateSpecification extends AbstractSpecification<MaterialDocItem> {


    public MaterialDocItemUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MaterialDocItem materialdocitem) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
