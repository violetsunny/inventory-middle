package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

import java.util.List;

/**
 * BOM配置BO（创建/更新请求）
 */
@Data
public class BomCaseConfigurationBO {

    private String tenantId;
    private String userId;
    private String userName;

    private BomCaseBO bomCase;
    private BomNodeBO parent;
    private List<BomNodeBO> children;
}
