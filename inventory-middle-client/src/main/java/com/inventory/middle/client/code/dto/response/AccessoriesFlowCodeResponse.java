package com.inventory.middle.client.code.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 备件流转码的查询返回对象
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class AccessoriesFlowCodeResponse implements Serializable {

    /**
     * 业务编号
     */
    private String buisnessNo;
    /**
     * 产生码记录的源单号
     */
    private String sourceNo;
    /**
     * 码的类型
     */
    private String type;
    /**
     * 码的类型
     */
    private String typeDescription;
    /**
     * 码记录的值
     */
    private String code;
    /**
     * 内部码
     */
    private String innerCode;
    /**
     * 码记录的发布者
     */
    private String publisher;
    /**
     * 码记录的前一任持有者
     */
    private String preOwner;
    /**
     * 码记录的当前持有者
     */
    private String currentOwner;
    /**
     * 码记录的状态
     */
    private String status;
    /**
     * 码记录的状态
     */
    private String statusDescription;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单行号
     */
    private String orderLineNo;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 该批次码的当前持有者名称（经销商）
     */
    private String currentOwnerName;
    /**
     * 该批次码的前一任持有者名称（经销商）
     */
    private String preOwnerName;
    /**
     * 逻辑仓编号
     */
    private String logicalPlantNo;
    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 发货单号
     */
    private String deliveryOrderNo;

}
