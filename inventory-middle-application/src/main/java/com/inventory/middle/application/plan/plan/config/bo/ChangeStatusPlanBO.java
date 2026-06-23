package com.inventory.middle.application.plan.plan.config.bo;

import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 计划方案配置
 *
 * @author caosheng
 * @date 2021/10/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChangeStatusPlanBO extends PlanBaseBO implements Serializable {

    private static final long serialVersionUID = -219452992837188009L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

}
