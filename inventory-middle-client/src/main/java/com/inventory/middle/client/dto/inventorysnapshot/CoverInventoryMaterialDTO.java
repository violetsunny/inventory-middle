/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 覆盖物料数据
 *
 * @author kanglele
 * @version $Id: CoverInventoryMaterialDTO, v 0.1 2022/4/30 18:00 kanglele Exp $
 */
@Data
public class CoverInventoryMaterialDTO extends BaseRequest {

    /**
     * 库存地点
     */
    @NotBlank(message = "storageLocationNo 库存地点不能为空")
    private String storageLocationNo;
    /**
     * 物料code
     */
    @NotBlank(message = "materialCode 物料不能为空")
    private String materialCode;

}
