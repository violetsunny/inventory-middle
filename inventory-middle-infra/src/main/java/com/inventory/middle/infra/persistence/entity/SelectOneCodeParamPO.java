package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

/**
 * 查询一个码的参数对象
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class SelectOneCodeParamPO {

    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 码记录的发布者
     */
    private String publisher;
    /**
     * 码记录的当前持有者
     */
    private String currentOwner;
    /**
     * 码记录的类型
     */
    private String type;
    /**
     * 码记录的值
     */
    private String code;
}
