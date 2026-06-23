package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;

import java.util.Date;

/**
 * pl_demand_supply_source 供需数据
 * <p>
 * 迁移自 com.enn.plan.management.dal.po.DemandSupplySourcePO
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Data
public class DemandSupplySourcePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 日期
     */
    private Date planDate;

    /**
     * 来源类型（2：客户独立需求，3：计划独立需求，4：BOM相关需求，5：计划订单，6：采购申请，7：采购订单，8：交运单）
     */
    private Integer sourceType;

    /**
     * 供需类型（1：需求，2：供给）
     */
    private Integer bizType;

    /**
     * 数量
     */
    private Long amount;

    /**
     * 单据
     */
    private String voucherNo;

    /**
     * 行号
     */
    private String documentNo;

    /**
     * 关联业务id
     */
    private String businessIdRef;

    /**
     * 异常码
     */
    private String exceptionCode;

    /**
     * 是否已删除（0-未删除/1-已删除，默认0）
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
