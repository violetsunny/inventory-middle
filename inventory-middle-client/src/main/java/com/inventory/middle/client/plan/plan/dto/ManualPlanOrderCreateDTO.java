package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 手动创建计划订单DTO
 * @date 2021/10/19 14:18
 */
@Data
public class ManualPlanOrderCreateDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -2706499991913093912L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 计划订单量
     */
    private Integer planOrderQuantity;

    /**
     * 库存单位
     */
    private String unit;

    /**
     * 预测库存
     */
    private Integer forecastInventory;

    /**
     * 计划下发日期
     */
    private Date planIssueTime;

    /**
     * 计划收货日期
     */
    private Date planReceivingTime;

}
