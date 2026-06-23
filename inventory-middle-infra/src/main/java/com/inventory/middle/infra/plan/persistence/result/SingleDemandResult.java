package com.inventory.middle.infra.plan.persistence.result;

import lombok.Data;

import java.util.Date;

@Data
public class SingleDemandResult {

    private Date planDate;

    private Long amount;

    private Integer type;

    private String extInfo;
}
