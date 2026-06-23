package com.inventory.middle.client.code.dto;


import com.inventory.middle.client.code.enums.CodeApplyOrderChannelEnum;
import com.inventory.middle.client.code.enums.CodeApprovalStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderDTO implements Serializable {

    private static final long serialVersionUID = 4096313383692907810L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 申请单创建渠道
     * @see CodeApplyOrderChannelEnum
     */
    private Integer channel;

    /**
     * 申请单创建渠道描述
     */
    private String channelDesc;

    /**
     * 原经销商-申请时流转码所属经销商
     */
    private String originalDistributorId;

    /**
     * 原经销商名称-申请时流转码所属经销商名称
     */
    private String originalDistributorName;

    /**
     * 发起方id
     */
    private String inviterId;

    /**
     * 发起方名称
     */
    private String inviterName;

    /**
     * 申请单编码
     */
    private String orderNo;

    /**
     * 厂商id
     */
    private String ownerId;

    /**
     * 厂商name
     */
    private String ownerName;

    /**
     * 受邀者-接收经销商
     */
    private String inviteeId;

    /**
     * 受邀者-接收经销商名称
     */
    private String inviteeName;

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
     * @see CodeApprovalStatusEnum
     */
    private Integer approvalStatus;

    /**
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 审批原因
     */
    private String approvalReason;

    /**
     * 申请单流转码数量
     */
    private Long orderCodeQty;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updatorId;

    /**
     * 更新时间
     */
    private Date updateTime;

}
