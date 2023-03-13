package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.LogicalPlant;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 逻辑仓库表Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class LogicalPlantUpdateSpecification extends AbstractSpecification<LogicalPlant> {


    public LogicalPlantUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(LogicalPlant logicalplant) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
