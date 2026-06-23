package com.inventory.middle.domain.model.bo.mq;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预警规则年检提醒消息体
 * @author dongguo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorAnnualInspectionMessageBO implements Serializable {

    private static final long serialVersionUID = -3473845964012146882L;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 年检时间
     */
    private String annualDate;

    /**
     * 年检周期
     */
    private String annualCycleDays;

}
