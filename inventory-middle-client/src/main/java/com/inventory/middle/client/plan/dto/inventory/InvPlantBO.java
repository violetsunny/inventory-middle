package com.inventory.middle.client.plan.dto.inventory;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 库存域实体：仓库BO
 *
 * @author Danny.Lee (migrated from scm-plan-management)
 * @date 2022/4/12
 */
@Data
@ToString
public class InvPlantBO implements Serializable {

    private static final long serialVersionUID = 8037970839989542163L;

    /**
     * 仓库ID
     */
    private Long plantId;

    /**
     * 仓库编码
     */
    private String plantCode;

    /**
     * 仓库名称
     */
    private String plantName;

    /**
     * 租户ID
     */
    private String tenantId;
}
