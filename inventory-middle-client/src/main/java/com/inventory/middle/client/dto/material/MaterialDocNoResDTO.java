/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kll
 * @version $Id: MaterialDocNoResDTO, v 0.1 2021/6/18 9:55 Exp $
 */
@Data
public class MaterialDocNoResDTO extends MaterialDocMsgResDTO implements Serializable {

    /**
     * 物料凭证号
     */
    private String materialDocNo;

    /**
     * 库存批次编号
     */
    private String batchNo;

    /**
     * 物料批次
     */
    private List<MaterialBatchNoDTO> materialBatchNos;

    /**
     * 物料凭证号-全量
     */
    private String fullMaterialDocNo;

}
