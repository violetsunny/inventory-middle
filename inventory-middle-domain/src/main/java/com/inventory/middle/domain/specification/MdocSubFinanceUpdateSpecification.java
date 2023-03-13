package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MdocSubFinance;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证-标签-财务Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public class MdocSubFinanceUpdateSpecification extends AbstractSpecification<MdocSubFinance> {


    public MdocSubFinanceUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MdocSubFinance mdocsubfinance) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
