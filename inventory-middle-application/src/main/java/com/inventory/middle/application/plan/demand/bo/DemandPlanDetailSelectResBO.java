package com.inventory.middle.application.plan.demand.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/10 10:23
 */
@Data
@Builder
public class DemandPlanDetailSelectResBO implements Serializable {

    private static final long serialVersionUID = 258413105789855190L;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 需求汇总周期1:日,2:周,3:月,默认1
     */
    private Integer aggregationPeriod;

    /**
     * 需求展望期开始时间
     */
    private Date demandHorizonBeginTime;

    /**
     * 需求展望期结束时间
     */
    private Date demandHorizonEndTime;

    /**
     * 物料列表
     */
    private List<DemandPlanDetailResultBO> materialList;
}
