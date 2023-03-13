package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证子表-数量Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class MdocSubQuantityUpdateSpecification extends AbstractSpecification<MdocSubQuantity> {


    public MdocSubQuantityUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MdocSubQuantity mdocsubquantity) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
