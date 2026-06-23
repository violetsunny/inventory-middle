package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新计划状态dto
 *
 * @author caosheng
 * @date 2021/10/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChangePlanStatusDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 8125455389605479792L;
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
