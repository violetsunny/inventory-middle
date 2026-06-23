package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @date: 2021/9/27 17:49
 * @author:xiaokang
 **/

@Data
public class DemandPlanCreateReqDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = 199154297164450140L;
    /**
     * 逻辑仓编码
     *
     */

    @NotNull(message = "逻辑仓编码不可为空!")
    private String logicalPlantNo;


    /**
     * 需求汇总周期1:日,2:周,3:月,默认1
     */
    @NotNull(message = "需求汇总周期不可为空!")
    private Integer aggregationPeriod;

    /**
     * 需求展望期开始时间
     */
    @NotNull(message = "需求展望期开始时间不可为空!")
    private Date demandHorizonBeginTime;

    /**
     * 需求展望期结束时间
     */
    @NotNull(message = "需求展望期结束时间不可为空!")
    private Date demandHorizonEndTime;

    /**
     * 需求类型
     */
    @NotNull(message = "需求类型")
    private Integer demandType;

    /**
     * 需求来源
     */
    @NotNull(message = "需求来源")
    private Integer demandSourceType;
}
