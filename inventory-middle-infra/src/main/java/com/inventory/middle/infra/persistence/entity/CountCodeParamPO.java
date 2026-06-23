package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

/**
 * @author hjs
 * @date 2021/12/14
 */

/**
 * 查询code表的参数PO对象
 */
@Data
public class CountCodeParamPO {

    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 厂商
     */
    private String publisher;
    /**
     * 厂商创建码的源批次号
     */
    private String sourceNo;
    /**
     * 码的当前持有者，经销商
     */
    private String currentOwner;

    private String type;
}
