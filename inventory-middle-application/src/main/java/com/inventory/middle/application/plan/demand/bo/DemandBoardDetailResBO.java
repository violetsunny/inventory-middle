package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/6 9:19
 */

@Data
public class DemandBoardDetailResBO implements Serializable {

    private static final long serialVersionUID = -1239307692697021718L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 单位
     */
    private String materialUnit;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 需求列表
     */
    private List<SingleDemandResultBO> demandList;

}
