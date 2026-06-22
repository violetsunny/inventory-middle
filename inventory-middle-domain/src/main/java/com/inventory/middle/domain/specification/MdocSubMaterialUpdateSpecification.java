package com.inventory.middle.domain.specification;

import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import top.kdla.framework.domain.shared.AbstractSpecification;

/**
 * 物料凭证子表-物料信息Specification
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public class MdocSubMaterialUpdateSpecification extends AbstractSpecification<MdocSubMaterial> {


    public MdocSubMaterialUpdateSpecification() {
    }

    @Override
    public boolean isSatisfiedBy(MdocSubMaterial mdocsubmaterial) {
        //逻辑根据自己的实际情况来编写
        return true;
    }
}
