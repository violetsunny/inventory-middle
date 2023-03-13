package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MdocSubMap;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证-标签-移动平均价Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class MdocSubMapUpdateSpecification extends AbstractSpecification<MdocSubMap> {


    public MdocSubMapUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MdocSubMap mdocsubmap) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
