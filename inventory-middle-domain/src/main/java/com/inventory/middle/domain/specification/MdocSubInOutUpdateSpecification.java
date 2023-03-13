package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MdocSubInOut;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证子表-出入库信息Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class MdocSubInOutUpdateSpecification extends AbstractSpecification<MdocSubInOut> {


    public MdocSubInOutUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MdocSubInOut mdocsubinout) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
