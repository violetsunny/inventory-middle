/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kll
 * @version $Id: UpdateMaterialDocMapDTO, v 0.1 2021/6/29 10:51 Exp $
 */
@Data
public class UpdateMaterialDocMapBO implements Serializable {

    /**
     * 平均价code
     */
    private String mapCode;
    /**
     * 平均价子code
     */
    private String mapSubCode;
    /**
     * 状态
     */
    private Integer mapStatus;
    /**
     * 租户
     */
    private String tenantId;

}
