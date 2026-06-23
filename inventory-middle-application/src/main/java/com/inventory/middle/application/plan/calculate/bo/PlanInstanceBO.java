package com.inventory.middle.application.plan.calculate.bo;

import com.inventory.middle.domain.plan.common.enums.InstanceStatusEnum;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.google.common.collect.Lists;
import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 计划执行实例
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlanInstanceBO extends BaseBo {

    private static final long serialVersionUID = 1056259754221117310L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 方案id
     */
    private Long planId;

    /**
     * 方案编码
     */
    private String planCode;

    /**
     * 计划版本号
     */
    private String planVersion;

    /**
     * 方案类型
     */
    private Integer planType;

    /**
     * 计划实例状态（0-未开始/1-执行中/2-已失败/3-已完结）
     */
    private Integer status;

    /**
     * 计划执行开始时间
     */
    private Date calStartTime;

    /**
     * 计划执行结束时间
     */
    private Date calEndTime;

    /**
     * 是否删除（0-未删除/1-已删除，默认0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建人（0-系统）
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    private String creatorId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料计划实例集合<br/>
     */
    private volatile List<MaterialPlanInstanceBO> materialPlanInstances;

    /**
     * 互斥信号量
     */
    private final Object mutex = new Object();

    public void appendMaterialPlanInstance(MaterialPlanInstanceBO materialPlanInstance) {
        if (null == materialPlanInstance) {
            return;
        }
        if (null == materialPlanInstances) {
            synchronized (mutex) {
                if (null == materialPlanInstances) {
                    materialPlanInstances = Lists.newCopyOnWriteArrayList();
                }
            }
        }
        materialPlanInstances.add(materialPlanInstance);
    }

    @Override
    public String toLog() {
        return this.toString();
    }

    public static PlanInstanceBO initWithStatus(PlanBO plan, InstanceStatusEnum status) {
        PlanInstanceBO planInstance = new PlanInstanceBO();
        planInstance.setPlanId(plan.getId());
        planInstance.setPlanCode(plan.getPlanCode());
        planInstance.setPlanType(plan.getPlanType());
        planInstance.setStatus(status.getCode());
        planInstance.setCalStartTime(new Date());
        planInstance.setTenantId(plan.getTenantId());
        return planInstance;
    }
}
