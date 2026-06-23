/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service;


import com.inventory.middle.domain.model.bo.material.*;

import java.util.Date;

/**
 *  物料凭证-子项
 * @author kll
 * @version $Id: MaterialDocSubService, v 0.1 2021/6/16 17:10 Exp $
 */
public interface MaterialDocSubCoreService {

    /**
     * 获取物料凭证-物料信息
     * @param materialDocItemId
     * @return
     */
    MaterialDataBO getMaterialData(Long materialDocItemId);

    /**
     * 获取物料凭证-出入库信息
     * @param materialDocItemId
     * @return
     */
    WarehouseDataBO getWarehouseData(Long materialDocItemId);

    /**
     * 获取物料凭证-数量
     * @param materialDocItemId
     * @return
     */
    QuantityAndAmountDataBO getQuantityData(Long materialDocItemId);

    /**
     * 获取物料凭证-财务
     * @param materialDocItemId
     * @return
     */
    FinancialDataBO getFinancialData(Long materialDocItemId);

    /**
     * 获取物料凭证-MAP
     * @param materialDocItemId
     * @return
     */
    MapJournalDataBO getMapJournalData(Long materialDocItemId);

    /**
     * 获取物料凭证-补充信息
     * @param materialDocItemId
     * @return
     */
    MaterialExtDataBO getMaterialExtData(Long materialDocItemId);

    /**
     * 填充物料凭证子项
     * @param materialDocument
     */
    void supplementMaterialDoc(MaterialDocumentBO materialDocument);

    /**
     * 根据凭证ID查询出入库信息
     * @param materialDocId
     */
    WarehouseDataBO getWarehouseDataByDocId(Long materialDocId);

    /**
     * 更新map
     * @param updateMaterialDocMapBO
     */
    void updateMaterialDocMap(UpdateMaterialDocMapBO updateMaterialDocMapBO);


    /**
     * 更新年检信息
     * @param materialDocItemId
     * @return
     */
    boolean updateLocalAnnualDate(Long materialDocItemId, Date annualDate);
}
