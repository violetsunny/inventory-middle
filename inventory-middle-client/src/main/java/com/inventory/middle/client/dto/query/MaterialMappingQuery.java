/**
 * kanglele Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.query;

import lombok.Data;
import top.kdla.framework.dto.Query;

import javax.validation.constraints.NotEmpty;

/**
 * @author kanglele
 * @version $Id: GetMaterialMappingDTO, v 0.1 2021/9/27 16:44 Exp $
 */
@Data
public class MaterialMappingQuery extends Query {

    /**
     * @see
     */
    @NotEmpty
    private String materialDocSource;

}
