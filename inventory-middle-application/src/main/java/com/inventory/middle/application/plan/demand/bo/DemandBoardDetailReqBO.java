package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/9/29 16:49
 */

@Data
public class DemandBoardDetailReqBO {
    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 物料编码列表
     */
    private List<String> materialCodeList;

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
}
