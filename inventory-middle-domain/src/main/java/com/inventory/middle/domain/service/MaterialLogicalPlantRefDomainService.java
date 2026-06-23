package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.MaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.PageQueryMaterialPlantRefRequestBO;
import com.inventory.middle.domain.model.bo.material.QueryMaterialLogicalPlantRefBO;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

/** 物料和逻辑仓间关系业务服务接口 */
public interface MaterialLogicalPlantRefDomainService {
    boolean batchCreate(List<CreateMaterialLogicalPlantRefBO> creteBOList);
    List<MaterialLogicalPlantRefBO> getListByCondition(QueryMaterialLogicalPlantRefBO queryBO);
    PageResponse<MaterialLogicalPlantRefBO> pageQuery(PageQueryMaterialPlantRefRequestBO bo);
}
