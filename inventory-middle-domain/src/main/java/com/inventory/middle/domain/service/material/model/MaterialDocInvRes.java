/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.model;

import com.inventory.middle.domain.model.bo.material.MaterialBatchNoBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author kanglele
 * @version $Id: MaterialDocInvRes, v 0.1 2021/8/20 10:36 Exp $
 */
@Data
public class MaterialDocInvRes implements Serializable {

    private String materialDocNo;

    /**
     * 多个批次号=','号隔开
     */
    private String batchNo;

    /**
     * 物料凭证号-全量=','号隔开
     */
    private String fullMaterialDocNo;

    /**
     * 物料和批次关系
     */
    private List<MaterialBatchNoBO> materialBatchNos;
}
