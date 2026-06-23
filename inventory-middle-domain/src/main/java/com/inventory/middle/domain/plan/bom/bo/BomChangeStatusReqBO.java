package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * BOM启用/停用请求BO
 */
@Data
public class BomChangeStatusReqBO implements Serializable {

    private static final long serialVersionUID = 8472612424143860717L;

    private String tenantId;
    private String userId;
    private String userName;

    @NotNull(message = "主键id不为空")
    private Long id;

    private Integer status;
}
