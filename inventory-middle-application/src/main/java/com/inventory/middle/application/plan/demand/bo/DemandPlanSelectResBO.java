package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/10/8 15:37
 */
@Data
public class DemandPlanSelectResBO implements Serializable {

    private static final long serialVersionUID = 4782370614443121709L;

    /**
     * 需求计划id
     */
    private Long id;

    /**
     * 需求计划版本号
     */
    private String planVersion;

    /**
     * 公司主体（公司名字）
     */
    private String companyName;

    /**
     * 逻辑仓（逻辑仓编码）
     */
    private String logicalPlantNo;

    /**
     * 物理仓（物理仓编码）
     */
    private String warehouseNo;

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
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

    /**
     * 需求类型
     */
    private Integer demandType;

    /**
     * 需求来源
     */
    private Integer demandSourceType;
}
