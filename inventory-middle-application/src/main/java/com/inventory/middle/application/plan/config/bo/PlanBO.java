package com.inventory.middle.application.plan.config.bo;

import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 计划方案配置
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlanBO extends BaseBo implements Serializable {

    private static final long serialVersionUID = -819452997501188009L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 方案编码
     */
    private String planCode;

    /**
     * 覆盖物料类型 0-所有物料 1-指定物料
     */
    private Integer coverMaterialType;

    /**
     * 方案描述
     */
    private String planDesc;

    /**
     * 方案类型
     */
    private Integer planType;

    /**
     * 计划展望期(天)
     */
    private Integer planHorizon;

    /**
     * 计划开始时间
     */
    private Date planStartTime;

    /**
     * 计划开始时间 字符串类型
     */
    private String planStartTimeStr;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    /**
     * 更新时间 字符串类型
     */
    private String updateTimeStr;

    /**
     * 需求文件Id（逗号分割）
     */
    private List<String> demandPlanIds;

    /**
     * 需求文件 key->id value->name
     */
    private Map<Long, String> demandPlanFile;

    /**
     * 覆盖逻辑仓 key->no value->name
     */
    private Map<String, String> coverLogicalPlant;

    /**
     * 覆盖逻辑仓编码（逗号分割）
     */
    private List<String> coverLogicalPlantNos;

    /**
     * 投放参数（JSON结构）
     */
    private Map<String, Object> planDeliveryParams;

    /**
     * 计算参数（JSON结构）
     */
    private Map<String, Object> planCalculateParams;

    /**
     * 是否导入物料清单 0 否 1 是
     */
    private Integer exported;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 更新人id
     */
    private String updateUserId;

    private String creatorId;

    @Override
    public String toLog() {
        return toString();
    }

    public Date getPlanEndTime() {
        LocalDate planStartDate = DateUtils.toLocalDate(planStartTime);
        // 由于计划方案计算包含planStartTime，因此计算结束日期时需要扣减一天
        return DateUtils.toDate(planStartDate.plusDays(planHorizon - 1));
    }

    /**
     * 是否关联bom(0：不关联，1关联，默认0)
     */
    private Integer relatedBom;
}
