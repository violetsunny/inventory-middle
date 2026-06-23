/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.EnumResponse;
import com.inventory.middle.client.dto.material.*;
import com.inventory.middle.client.dto.material.in.InboundMaterialDocDTO;
import com.inventory.middle.client.dto.material.out.OutboundMaterialDocDTO;
import com.inventory.middle.client.dto.material.outin.OutInboundMaterialDocDTO;
// RDFA import removed;
// RDFA import removed;

/**
 * 物料凭证操作
 * @author kll
 * @version $Id: MaterialDocService, v 0.1 2021/6/17 19:38 Exp $
 */
public interface MaterialDocService {

    /**
     * 获取移动类型映射关系
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialMappingDTO> queryMaterialTypeMapping(GetMaterialMappingDTO req);

    /**
     * 查询物料凭证
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocumentResDTO> getMaterialDocument(GetMaterialDocumentReqDTO req);

    /**
     * 查询物料批次
     * @param query
     * @return
     */
    top.kdla.framework.dto.SingleResponse<QueryMaterialBatchNoResDTO> queryMaterialBatchNo(QueryMaterialBatchNoReqDTO query);

    /**
     * 生成物料凭证
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocNoResDTO> createMaterialDocument(MaterialDocumentDTO req);

    /**
     * 入库生成物料凭证
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocNoResDTO> inboundMaterialDoc(InboundMaterialDocDTO req);

    /**
     * 出库生成物料凭证
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocNoResDTO> outboundMaterialDoc(OutboundMaterialDocDTO req);

    /**
     * 物料凭证冲销
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocNoResDTO> reverseMaterialDoc(ReverseMaterialDocumentDTO req);

    /**
     * 出入库生成物料凭证
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocNoResDTO> outInboundMaterialDoc(OutInboundMaterialDocDTO req);

    /**
     * 校验物料凭证参数
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<MaterialDocMsgResDTO> checkMaterialDocument(MaterialDocumentDTO req);

    /**
     * 分页查询物料凭证
     * @param req
     * @return
     */
    top.kdla.framework.dto.PageResponse<PagedMaterialDocumentResDTO> pageList(PagedMaterialDocumentReqDTO req);

    /**
     * 查询物料类型
     * @return
     */
    top.kdla.framework.dto.PageResponse<EnumResponse<Integer>> queryMaterialDocType();


    /**
     * 查询物料组
     * @return
     */
    top.kdla.framework.dto.PageResponse<EnumResponse<String>> queryMaterialDocGroup();

    /**
     * 导出集合查询
     * @param req
     * @return
     */
    top.kdla.framework.dto.PageResponse<ExportMaterialDocumentResDTO> exportList(ExportMaterialDocumentReqDTO req);

    /**
     * 更新物料凭证MAP
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> updateMaterialDocMap(UpdateMaterialDocMapDTO req);

    /**
     * 获取下次年检日期
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<String> getNextAnnualDate(UpdateMaterialAnnualDateReqDTO req);

    /**
     * 更新年检时间
     * @param req
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Boolean> updateMaterialAnnualDate(UpdateMaterialAnnualDateReqDTO req);

    /**
     * 获取物料凭证id
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Long> getMaterialDocId();
}
