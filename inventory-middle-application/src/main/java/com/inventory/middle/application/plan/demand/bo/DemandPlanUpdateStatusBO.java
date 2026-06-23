package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 20:18
 */
@Data
public class DemandPlanUpdateStatusBO  extends PlanBaseBO implements Serializable {
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
