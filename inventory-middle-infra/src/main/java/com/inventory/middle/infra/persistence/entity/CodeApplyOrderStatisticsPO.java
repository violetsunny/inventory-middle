package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-31 17:17:01
 */
@Data
public class CodeApplyOrderStatisticsPO implements Serializable {

    private static final long serialVersionUID = 2332678877639601775L;

    /**
     * 申请单明细流转码数量
     */
    private Long quantity;

    /**
     * 申请单id
     */
    private Long applyOrderId;
}
