package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderDetailParamPO implements Serializable {

    private static final long serialVersionUID = 87385187524936700L;

    /**
     * 所有者id （厂商）
     */
    private String ownerId;


    /**
     * 审批状态 0-未申请；1-未审批；2-审批通过；3-审批已拒绝
     */
    private List<Integer> approvalStatusList;


}
