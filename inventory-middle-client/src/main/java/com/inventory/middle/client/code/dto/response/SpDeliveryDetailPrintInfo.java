package com.inventory.middle.client.code.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hjs
 * @date 2021/12/23
 */
@Data
public class SpDeliveryDetailPrintInfo implements Serializable {

    /**
     * 经销商id
     */
    private String distributorId;

    /**
     * 经销商名称
     */
    private String distributorName;

    /**
     * 厂商id
     */
    private String manufacturerId;

    /**
     * 厂商名称
     */
    private String manufacturerName;

    /**
     *  标识码
     */
    private String markCode;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 发货单号
     */
    private String deliveryOrderNo;

    /**
     * CRM订单号
     */
    private String crmOrderNo;

    /**
     * ERP订单号
     */
    private String outOrderNo;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 条形码
     */
    private String barCode;

    /**
     * 流转码
     */
    private String code;

    /**
     * 检验员编码
     */
    private String inspectorNo;

    /**
     * 该流转码对应的内部码
     */
    private String innerCode;
}
