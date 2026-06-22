package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.Warehouse;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物理仓库表Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class WarehouseUpdateSpecification extends AbstractSpecification<Warehouse> {


    public WarehouseUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(Warehouse warehouse) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
