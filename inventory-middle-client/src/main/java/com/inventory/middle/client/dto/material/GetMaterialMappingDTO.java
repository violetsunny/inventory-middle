/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.enums.MaterialDocSourceEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
/**
 * @author kanglele
 * @version $Id: GetMaterialMappingDTO, v 0.1 2021/9/27 16:44 Exp $
 */
@Data
public class GetMaterialMappingDTO extends BaseRequest {

    /**
     * @see MaterialDocSourceEnum
     */
    @NotEmpty
    private String materialDocSource;

}
