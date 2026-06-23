package com.inventory.middle.client.plan.bom.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author caosheng
 * @title: BomChangeStatusDTO
 * @projectName scm-plan-management
 * @description: bom启用停用
 * @date 2021/12/6 16:11
 */
@Data
public class BomChangeStatusReqDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -1554539006484536292L;

    /**
     * 主键
     */
    @NotNull(message = "主键id不为空")
    private Long id;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

}
