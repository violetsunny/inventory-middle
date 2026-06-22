package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.ShipmentLine;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 交运单明细Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
public class ShipmentLineUpdateSpecification extends AbstractSpecification<ShipmentLine> {


    public ShipmentLineUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(ShipmentLine shipmentline) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
