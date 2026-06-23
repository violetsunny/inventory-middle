package com.inventory.middle.client.code.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 批量创建备件流转码请求
 *
 * @author hjs
 * @date 2021/12/14
 */
@Data
public class BatchCreateCodeItemRequest implements Serializable {

    /**
     * 该批次码的源编号
     */
    @NotBlank(message = "sourceNo不能为空")
    private String sourceNo;
    /**
     * 该批次码的发布者（厂商）
     */
    @NotBlank(message = "publisher不能为空")
    private String publisher;
    /**
     * 该批次码的当前持有者（经销商）
     */
    @NotBlank(message = "currentOwner不能为空")
    private String currentOwner;
    /**
     * 需要生成的码的数量
     */
    @NotNull(message = "count不能为空")
    private Integer count;


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
    @Size(max = 40, message = "物料编码超过最大限制40")
    @NotBlank(message = "materialCode不能为空")
    private String materialCode;
    /**
     * 物料名称
     */
    @Size(max = 120, message = "物料名称超过最大限制120")
    private String materialName;
    /**
     * 该批次码的当前持有者名称（经销商）
     */
    private String currentOwnerName;
    /**
     * 逻辑仓编号
     */
    @NotBlank(message = "logicalPlantNo不能为空")
    private String logicalPlantNo;
    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    @NotBlank(message = "发货单号不能为空")
    private String deliveryOrderNo;
}
