package com.inventory.middle.client.plan.config.dto;

import com.inventory.middle.client.plan.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询计划订单 DTO
 * @date 2021/10/20 17:30
 */
@Data
public class PlanOrderPageReqDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = -3382665183980079503L;

    private String orderNo;

    private Date maxCreateTime;

    private Date minCreateTime;

    private Integer status;

    private String materialCode;

    private String logicalPlantNo;

    private String tenantId;

}
