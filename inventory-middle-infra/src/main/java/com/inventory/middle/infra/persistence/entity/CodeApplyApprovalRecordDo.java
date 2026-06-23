package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 17:11:10
 */
@Data
public class CodeApplyApprovalRecordDo extends BasePO implements Serializable {

    private static final long serialVersionUID = -524424933841365668L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id （厂商）
     */
    private String tenantId;

    /**
     * 申请单id
     */
    private Long applyOrderId;

    /**
     * 审批状态 0-未申请；1-未审批；2-审批通过；3-审批已拒绝
     */
    private int approvalStatus;

    /**
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 审批原因
     */
    private String approvalReason;


}
