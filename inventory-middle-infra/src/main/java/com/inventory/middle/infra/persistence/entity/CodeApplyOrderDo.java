package com.inventory.middle.infra.persistence.entity;

import com.inventory.middle.infra.persistence.entity.ExtendParamEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderDo extends BaseExtendPO implements Serializable {

    private static final long serialVersionUID = -162197171410330278L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 申请单创建渠道
     */
    private Integer channel;

    /**
     * 租户id （经销商）
     */
    private String tenantId;

    /**
     * 申请单编码
     */
    private String orderNo;

    /**
     * 所有者-厂商
     */
    private String ownerId;

    /**
     * 原经销商-申请时流转码所属经销商
     */
    private String originalDistributorId;

    /**
     * 受邀者-接收经销商
     */
    private String inviteeId;

    /**
     * 受邀者逻辑仓编码
     */
    private String inviteeLogicalPlantNo;

    /**
     * 发起时间
     */
    private Date applyTime;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 审批状态 0-未申请；1-未审批；2-审批通过；3-审批已拒绝
     */
    private int approvalStatus;

    /**
     * 审批时间
     */
    private Date approvalTime;

    public Long getApplyApprovalRecordId(){
        String value = super.getExtendParamValue(ExtendParamEnum.CODE_APPLY_APPROVAL_ID.getKey());
        return StringUtils.isBlank(value) ? null : Long.parseLong(value.trim());
    }

    public void setApplyApprovalRecordId(Long applyApprovalId){
        String id = Objects.isNull(applyApprovalId) ? StringUtils.EMPTY : applyApprovalId.toString();
        super.addExtendParamMap(ExtendParamEnum.CODE_APPLY_APPROVAL_ID.getKey(), id);
    }
}
