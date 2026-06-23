package com.inventory.middle.client.code.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.BusinessNoEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 窜货申请 校验 请求
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class FleeingGoodsApplyCheckRequest implements Serializable {

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
     * 经销商
     */
    @NotBlank(message = "currentOwner不能为空")
    private String currentOwner;

    /**
     * 备件流转码列表
     */
    @NotEmpty(message = "codeList不能为空")
    private List<String> codeList;
}
