package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author zhouxinzhong
 * @date 2021/9/28 15:26
 */

@Data
public class DemandPlanSelectReqDTO implements Serializable {

    private static final long serialVersionUID = -4321014221213149318L;

    /**
     * 需求计划版本号
     */
    private String planVersion;

    /**
     * 公司主体（公司名字）
     */
    private String companyName;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 物理仓编码
     */
    private String warehouseNo;

    /**
     * 需求汇总周期1:日,2:周,3:月,默认1
     */
    private Integer aggregationPeriod;

    /**
     * 状态（0-已失效/1-已生效）
     */
    private Integer status;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 需求类型
     */
    private Integer demandType;

    /**
     * 需求来源
     */
    private Integer demandSourceType;
}
