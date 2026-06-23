package com.inventory.middle.client.service;

import java.util.ArrayList;
import java.util.List;

import com.inventory.middle.client.dto.inventorysnapshot.*;

// RDFA import removed;
// RDFA import removed;

/**
 * 库存快照服务
 * 
 * @author peisheng.wang
 * @date 2021-06-16
 * @version 1.0.0
 */
public interface InventorySnapshotService {

    top.kdla.framework.dto.PageResponse<PagedInventorySnapshotResDTO> pageList(PagedInventorySnapshotReqDTO request);

    top.kdla.framework.dto.PageResponse<DetailInventorySnapshotResDTO> detail(DetailInventorySnapshotReqDTO request);

    top.kdla.framework.dto.PageResponse<ExportInventorySnapshotResDTO> exportList(ExportInventorySnapshotReqDTO request);

    top.kdla.framework.dto.PageResponse<QueryCurrentInventoryResDTO> queryInventorySnapshotByBatchNo(QueryInventoryBatchNoReqDTO request);

    top.kdla.framework.dto.SingleResponse<ArrayList<QueryInventoryCountByMaterialCodeRespDTO>> queryInventorySnapshotByMaterialCode(QueryInventoryCountByMaterialCodeReqDTO request);

    top.kdla.framework.dto.SingleResponse<QueryCurrentInventoryResDTO> queryCurrentInventory(QueryCurrentInventoryReqDTO request);

    top.kdla.framework.dto.SingleResponse<ArrayList<ListQueryInventorySnapshotResDTO>> queryInventorySnapshotList(ListQueryInventorySnapshotReqDTO request);

    /**
     * 特殊：覆盖逻辑仓下物料老批次，只保留最新批次
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> coverLastBatchNo(CoverInventorySnapshotDTO req);
}
