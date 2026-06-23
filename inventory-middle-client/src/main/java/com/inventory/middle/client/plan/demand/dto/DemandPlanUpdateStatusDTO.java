package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 20:18
 */
@Data
public class DemandPlanUpdateStatusDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 5677047167866478537L;

    /**
     * 需求计划id
     */
    @NotNull
    private Long id;

    /**
     * 状态
     */
    private int status;



}
