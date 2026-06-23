package com.inventory.middle.infra.plan.persistence.condition.bom;

import lombok.Data;

import java.io.Serializable;

@Data
public class BomChangeStatusCondition implements Serializable {

    private static final long serialVersionUID = -903759255451698147L;

    private long id;

    private Integer status;
}
