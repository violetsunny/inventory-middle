package com.inventory.middle.client.plan.dto.inventory.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 物料库存查询请求
 *
 * @author Danny.Lee (migrated from scm-plan-management)
 * @date 2022/4/27
 */
@Data
public class MaterialInventoryRequest implements Serializable {

    private static final long serialVersionUID = -4667720833291323459L;

    private String materialCode;

    private String plantCode;

    private String tenant;
}
