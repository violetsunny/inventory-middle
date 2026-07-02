package com.inventory.middle.domain.repository;

import lombok.Data;

import java.util.Date;

@Data
public class CodeApplyOrderQueryParam {

    private Integer channel;

    private String applyOrderNo;

    private String ownerId;

    private String originalDistributorId;

    private String inviterId;

    private String inviteeId;

    private Integer approvalStatus;

    private Date applyBeginTime;

    private Date applyEndTime;

    private Date approvalBeginTime;

    private Date approvalEndTime;
}
