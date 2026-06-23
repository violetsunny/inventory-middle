package com.inventory.middle.domain.service;

import java.util.List;

import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.MaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.QueryMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.PageQueryMaterialPlantRefRequestBO;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
public interface MaterialLogicalPlantRefCoreService {

    /**
     * 新增
     * @param creteBO
     * @return
     */
    boolean create(CreateMaterialLogicalPlantRefBO creteBO);

    /**
     * 批量更新
     * @param creteBOList
     * @return
     */
    boolean batchCreate(List<CreateMaterialLogicalPlantRefBO> creteBOList);

    /**
     * 根据条件查询
     * @param queryBO
     * @return
     */
    List<MaterialLogicalPlantRefBO> getListByCondition(QueryMaterialLogicalPlantRefBO queryBO);

    /**
     * 分页查询
     * @param requestPO
     * @return
     */
    List<MaterialLogicalPlantRefBO> pageQuery(PageQueryMaterialPlantRefRequestBO requestBO);
}
