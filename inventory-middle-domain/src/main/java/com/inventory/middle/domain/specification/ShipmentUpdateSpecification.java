package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.Shipment;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 交运单Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class ShipmentUpdateSpecification extends AbstractSpecification<Shipment> {


    public ShipmentUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(Shipment shipment) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
