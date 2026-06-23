package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/9/29 9:53
 */

@Data
public class DemandBoardDetailReqDTO implements Serializable {

    private static final long serialVersionUID = -6133212203194659307L;

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
