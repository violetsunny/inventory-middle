package com.inventory.middle.domain.model.bo.monitor;

import com.inventory.middle.domain.model.bo.BaseBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryMonitorRuleBO extends BaseBO implements Serializable {

    private static final long serialVersionUID = -9147292176005470703L;

    /**
     * 规则code
     */
    private String monitorRuleCode;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 预警发送方式
     */
    private String monitorSendMode;

    /**
     * 发送地址
     */
    private String monitorSendAddress;

    /**
     * 预警类型
     */
    private String monitorType;

    /**
     * 预警维度
     */
    private String monitorDimension;

    /**
     * 任务间隔（分钟）
     */
    private Integer monitorInterval;

    /**
     * 规则启用状态
     */
    private String monitorEnableStatus;



}
