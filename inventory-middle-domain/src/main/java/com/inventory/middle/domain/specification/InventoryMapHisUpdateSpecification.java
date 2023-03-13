package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.InventoryMapHis;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 移动平均价历史记录Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class InventoryMapHisUpdateSpecification extends AbstractSpecification<InventoryMapHis> {


    public InventoryMapHisUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(InventoryMapHis inventorymaphis) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
