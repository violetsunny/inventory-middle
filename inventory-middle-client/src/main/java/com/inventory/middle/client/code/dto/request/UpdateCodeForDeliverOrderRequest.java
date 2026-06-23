package com.inventory.middle.client.code.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.BusinessNoEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 发货时，更新码信息的请求
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class UpdateCodeForDeliverOrderRequest implements Serializable {

    /**
     * 来源系统
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;
    /**
     * 业务编号
     */
    @EnumValidator(enumClass = BusinessNoEnum.class, checkMethod = "isValidCode", message = "不合法的businessNo值")
    @NotBlank(message = "businessNo不能为空")
    private String businessNo;
    /**
     * 这个码的发布方
     */
    @NotBlank(message = "publisher不能为空")
    private String publisher;
    /**
     * 这个码的当前持有方
     */
    @NotBlank(message = "currentOwner不能为空")
    private String currentOwner;
    /**
     * 这个码的生成批次号
     */
    @NotBlank(message = "sourceNo不能为空")
    private String sourceNo;

    /**
     * 这个码附带的最新的逻辑仓编号
     */
    @NotBlank(message = "logicalPlantNo不能为空")
    private String logicalPlantNo;
    /**
     * 这个码附带的最新的逻辑仓名称
     */
    @NotBlank(message = "logicalPlantName不能为空")
    private String logicalPlantName;

    /**
     * 操作人
     */
    @NotBlank(message = "operatorId不能为空")
    private String operatorId;

    @NotBlank(message = "来源系统不能为空")
    @Size(max = 20, message = "来源系统不能过长")
    private String sourceSystem;


}
