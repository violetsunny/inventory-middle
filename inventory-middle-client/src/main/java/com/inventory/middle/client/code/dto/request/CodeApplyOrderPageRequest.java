package com.inventory.middle.client.code.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderPageRequest implements Serializable {

    private static final long serialVersionUID = -807503384774460411L;

    /**
     * 申请单号
     */
    private String applyOrderNo;

    /**
     * 所有者id （厂商）
     */
    private String ownerId;

    /**
     * 发起方id
     */
    private String inviterId;

    /**
     * 原经销商-申请时流转码所属经销商
     */
    private String originalDistributorId;

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

    /**
     * 页大小
     */
    @NotNull(message = "页面size不能为空")
    @Min(value = 1, message = "页面size最少为1")
    private Integer pageSize;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum;

}
