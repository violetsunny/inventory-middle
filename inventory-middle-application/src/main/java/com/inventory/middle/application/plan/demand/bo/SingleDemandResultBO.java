package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/10/9 9:12
 */
@Data
public class SingleDemandResultBO {
    /**
     * 计划日期
     */
    private Date planDate;

    /**
     * 需求数量
     */
    private Long amount;

    /**
     * 需求种类
     */
    private Integer type;

    /**
     * 拓展信息
     */
    private String extInfo;
}
