package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderParamPO implements Serializable {

    private static final long serialVersionUID = 4104560601532773006L;
    /**
     * 申请单创建渠道
     */
    private Integer channel;
    /**
     * 申请单号
     */
    private String applyOrderNo;

    /**
     * 所有者id （厂商）
     */
    private String ownerId;

    /**
     * 原经销商-申请时流转码所属经销商
     */
    private String originalDistributorId;

    /**
     * 邀请者id （发起经销商）
     */
    private String inviterId;

    /**
     * 受邀者-接收经销商
     */
    private String inviteeId;

    /**
     * 审批状态 0-未申请；1-未审批；2-审批通过；3-审批已拒绝
     */
    private Integer approvalStatus;

    /**
     * 发起查询开始时间
     */
    private Date applyBeginTime;

    /**
     * 发起查询结束时间
     */
    private Date applyEndTime;

    /**
     * 审批查询开始时间
     */
    private Date approvalBeginTime;

    /**
     * 审批查询结束时间
     */
    private Date approvalEndTime;

}
