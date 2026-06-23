package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

/**
 * 查询一个码的参数对象
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class SelectOneCodeParam2PO {

    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 码记录的发布者
     */
    private String publisher;
    /**
     * 内部码
     */
    private String innerCode;
}
