/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

/**
 * @author kll
 * @version $Id: UpdateMaterialDocMapDTO, v 0.1 2021/6/29 10:51 Exp $
 */
@Data
public class UpdateMaterialDocMapDTO extends BaseRequest {

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
