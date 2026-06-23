package com.inventory.middle.client.service;

import java.util.ArrayList;
import java.util.List;

import com.inventory.middle.client.dto.material.*;
// RDFA import removed;
// RDFA import removed;

/**
 *  物料-逻辑仓关系
 * @author dongguo.tao
 * @date 2021-06-16
 */
public interface MaterialLogicalPlantRefService {


    /**
     * 批量创建
     * @param creteReqDTOList
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> batchCreate(List<CreateMaterialLogicalPlantRefReqDTO> creteReqDTOList);

    /**
     * 根据条件查询
     * @param queryDTO
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<MaterialLogicalPlantRefDTO>> getListByCondition(QueryMaterialLogicalPlantRefReqDTO queryDTO);

    /**
     * 分页查询 物料-逻辑仓关系
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<PageQueryMaterialPlantRefResponse> pageQuery(PageQueryMaterialPlantRefRequest request);

}
