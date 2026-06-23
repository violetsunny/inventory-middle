package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.material.qry.MaterialCodeListQry;
import com.inventory.middle.client.dto.material.qry.MaterialLogicalPlantQry;
import com.inventory.middle.client.dto.material.resp.InventoryMaterialResp;
import com.inventory.middle.client.dto.material.resp.MaterialLogicalPlantRefResp;
// RDFA import removed;

import java.util.ArrayList;

/**
 * 物料数据服务
 *
 * @author vincent.li
 * @date 2022/5/7 11:06
 */
public interface MaterialDataService {
    /**
     * 根据租户，物料编码批量查询物料信息
     * @param qry
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<InventoryMaterialResp>> listByMaterialCodeList(MaterialCodeListQry qry);
    /**
     * 根据租户id+逻辑仓编码获取物料列表
     * @param qry
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<MaterialLogicalPlantRefResp>>  queryMaterialLogicalPlantRef(MaterialLogicalPlantQry qry);

}
