package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.StorageLocation;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 存储地点表Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public class StorageLocationUpdateSpecification extends AbstractSpecification<StorageLocation> {


    public StorageLocationUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(StorageLocation storagelocation) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
