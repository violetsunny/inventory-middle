package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @date: 2021/9/27 17:49
 * @author:xiaokang
 **/

@Data
public class DemandPlanUpdateReqDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = 199154297164450140L;
    /**
     * 需求计划主键
     *
     */

    @NotNull(message = "需求计划主键不可为空!")
    private Long id;

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


}
