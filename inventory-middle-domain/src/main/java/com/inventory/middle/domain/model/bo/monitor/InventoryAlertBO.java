package com.inventory.middle.domain.model.bo.monitor;

import com.inventory.middle.domain.model.bo.BaseBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryAlertBO extends BaseBO implements Serializable {

    private static final long serialVersionUID = -2353211819446159593L;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 预警规则id
     */
    private Long monitorRuleId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 偏差数量
     */
    private BigDecimal deviate;

    /**
     * 报警标识
     */
    private String action;

    /**
     * 报警时间
     */
    private Date alertDate;

    /**
     * 报警状态（处理状态）
     */
    private String status;


}
