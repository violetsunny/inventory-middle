package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/10/9 10:19
 * @description 单日需求数量
 */
@Data
public class SingleDemandResultDTO implements Serializable {

    private static final long serialVersionUID = 7788558936740480433L;

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
