package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.mdata.qry.GetProductMasterDataSourceQry;

/**
 * 库存主数据源服务
 *
 * @author vincent.li
 * @date 2022/4/29 15:35
 */
public interface InventoryMasterDataSourceService {

    /**
     * 获取产品产品物料主数据源
     *
     * @param qry 获取产品产品物料主数据源的查询请求
     * @return top.kdla.framework.dto.SingleResponse<String>
     */
    top.kdla.framework.dto.SingleResponse<String> getProductMasterDataSource(GetProductMasterDataSourceQry qry);

}
