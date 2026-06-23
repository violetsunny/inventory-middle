/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.domain.model.bo.material.ExportMaterialDocumentReqBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.material.PagedMaterialDocumentReqBO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author kll
 * @version $Id: MaterialDocService, v 0.1 2021/6/16 18:55 Exp $
 */
public interface MaterialDocCoreService {

    /**
     * 物料凭证
     *
     * @param materialDocNo
     * @return
     */
    MaterialDocumentBO getMaterialDoc(String materialDocNo);

    /**
     * 根据来源单查询物料凭证
     *
     * @param originalNo
     * @return
     */
    MaterialDocumentBO getMaterialDocByOriginalNo(String originalNo);

    /**
     * 根据原始单据号查询
     *
     * @param originalNo
     * @return
     */
    MaterialDocumentBO queryByOriginalNoAndCate(String originalNo);

    /**
     * 物料凭证项
     *
     * @param materialDocId
     * @return
     */
    List<MaterialDocumentItemBO> getMaterialDocItem(Long materialDocId);

    /**
     * 创建物料凭证
     *
     * @param materialDocument
     * @return
     */
    MaterialDocumentBO createMaterialDoc(MaterialDocumentBO materialDocument);

    /**
     * 分页查询物料信息
     *
     * @param reqBO
     * @return
     */
    List<MaterialDocumentBO> pageList(PagedMaterialDocumentReqBO reqBO);

    /**
     * 查询导出集合
     *
     * @param reqBO
     * @return
     */
    List<MaterialDocumentBO> exportList(ExportMaterialDocumentReqBO reqBO);

    /**
     * 根据Id查询
     *
     * @param materialDocId
     * @return
     */
    MaterialDocumentBO getMaterialDocById(Long materialDocId);

    /**
     * 根据物料凭证ID、批次号、物料编码、查询物料凭证项
     *
     * @param materialDocId
     * @param batchNo
     * @param materialCode
     * @return
     */
    MaterialDocumentItemBO getMaterialDocItemByDocIdAndBatchNoAndMaterialCode(Long materialDocId, String batchNo, String materialCode);

    /**
     * @param batchNo
     * @param materialCode
     * @param tenantId
     * @return
     */
    MaterialDocumentItemBO getFirstMaterialDocItemByBatchNoAndMaterialCode(String batchNo, String materialCode, String tenantId);

    /**
     * 初始化Map需要数据
     * @param materialDocument
     */
    void initMap(MaterialDocumentBO materialDocument);

    /**
     * 发送库存变化MQ
     *
     * @param materialDocument
     */
    void sendInventoryInMQ(MaterialDocumentBO materialDocument, InventoryTagEnum inventoryTagEnum);

    /**
     * 根据id批量查询物料凭证
     *
     * @param materialDocIds
     * @return
     */
    List<MaterialDocumentBO> listMaterialDocByIds(List<Long> materialDocIds);

    /**
     * 重算批次价
     * @param list
     * @return
     */
    BigDecimal calBatchPrice(List<MaterialDocumentItemBO> list);

    /**
     * 根据移动类型获取批次号
     * @param materialAdjustType
     * @return
     */
    String getMaterialBatchNo(String materialAdjustType, String logicalPlantNo, String originalNo);

    /**
     * check唯一性
     * @param uniqueNo
     * @param appKey
     * @return
     */
    Boolean checkMdocUniqueNo(String uniqueNo, String appKey);
}
