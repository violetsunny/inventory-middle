package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 需求计划
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DemandPlanBO extends BaseBo {

    private static final long serialVersionUID = 2094930550247342430L;

    private Long id;

    /**
     * 逻辑仓编码
     *
     */

    @NotNull
    private String logicalPlantNo;


    /**
     * 需求类型
     */
    @NotNull
    private Integer demandType;

    /**
     * 需求来源
     */
    @NotNull
    private Integer demandSourceType;

    /**
     * 需求汇总周期1:日,2:周,3:月,默认1
     */
    @NotNull
    private Integer aggregationPeriod;

    /**
     * 需求展望期开始时间
     */
    @NotNull
    private Date demandHorizonBeginTime;

    /**
     * 需求展望期结束时间
     */
    @NotNull
    private Date demandHorizonEndTime;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;


    /**
     * 周期计划数量
     */
    private List<DemandPlanMaterialPeriodBO> periodList;



    @Override
    public String toLog() {
        return this.toString();
    }
}
