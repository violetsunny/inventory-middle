package com.inventory.middle.client.code.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.BusinessNoEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 备件流转码 占用 请求
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class AccessoriesFlowCodeUseRequest implements Serializable {

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
     * 备件流转码
     */
    @NotBlank(message = "accessoriesFlowCode不能为空")
    private String accessoriesFlowCode;

    /**
     * 当前的 备件流转码 的持有者
     */
    @NotBlank(message = "currentOwner不能为空")
    private String currentOwner;

//    @NotBlank(message = "operatorId不能为空")
    private String operatorId;

    @NotBlank(message = "来源系统不能为空")
    @Size(max = 20, message = "来源系统不能过长")
    private String sourceSystem;
}
