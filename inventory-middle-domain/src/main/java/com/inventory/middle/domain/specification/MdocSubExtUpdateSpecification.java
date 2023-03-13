package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MdocSubExt;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证-标签-扩展Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class MdocSubExtUpdateSpecification extends AbstractSpecification<MdocSubExt> {


    public MdocSubExtUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MdocSubExt mdocsubext) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
