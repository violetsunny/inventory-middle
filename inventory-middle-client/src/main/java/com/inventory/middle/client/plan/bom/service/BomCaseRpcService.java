package com.inventory.middle.client.plan.bom.service;

import com.inventory.middle.client.plan.bom.dto.BomCaseChildrenQueryReqDTO;
import com.inventory.middle.client.plan.bom.dto.BomCaseConfigurationDTO;
import com.inventory.middle.client.plan.bom.dto.BomCaseDetailDTO;
import com.inventory.middle.client.plan.bom.dto.BomCaseDetailReqDTO;
import com.inventory.middle.client.plan.bom.dto.BomCaseQueryReqDTO;
import com.inventory.middle.client.plan.bom.dto.BomCaseQueryResDTO;
import com.inventory.middle.client.plan.bom.dto.BomChangeStatusReqDTO;
import com.inventory.middle.client.plan.bom.dto.BomNodeDTO;
import com.inventory.middle.client.plan.bom.dto.BomTreeDTO;
import com.inventory.middle.client.plan.bom.dto.BomTreeRenderReqDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.ArrayList;

/**
 * BOM 相关 RPC 接口
 */
public interface BomCaseRpcService {

    /** 渲染生成bom树 */
    SingleResponse<ArrayList<BomTreeDTO>> renderBomTree(BomTreeRenderReqDTO requestDTO);

    /** 创建BOM */
    SingleResponse<String> create(BomCaseConfigurationDTO requestDTO);

    /** 更新BOM */
    SingleResponse<String> update(BomCaseConfigurationDTO requestDTO);

    /** bom启用/停用 */
    SingleResponse<Boolean> bomChangeStatus(BomChangeStatusReqDTO requestDTO);

    /** BOM清单（分页） */
    PageResponse<BomCaseQueryResDTO> pageQueryBom(BomCaseQueryReqDTO requestDTO);

    /** BOM单及母件详情 */
    SingleResponse<BomCaseDetailDTO> queryBomCaseDetail(BomCaseDetailReqDTO requestDTO);

    /** 分页查询子件详情 */
    PageResponse<BomNodeDTO> pageQueryChildrenDetail(BomCaseChildrenQueryReqDTO requestDTO);

    /** 查询子件详情（不分页） */
    SingleResponse<ArrayList<BomNodeDTO>> queryChildrenDetail(BomCaseDetailReqDTO requestDTO);
}
