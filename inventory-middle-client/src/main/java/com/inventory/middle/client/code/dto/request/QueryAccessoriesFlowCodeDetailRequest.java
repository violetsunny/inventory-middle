package com.inventory.middle.client.code.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.BusinessNoEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 查询单个码的详情信息的请求
 *
 * @author hjs
 * @date 2021/12/17
 */
@Data
public class QueryAccessoriesFlowCodeDetailRequest implements Serializable {

    /**
     * AppKey
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
     * 厂商
     */
    private String publisher;
    /**
     * 码的当前持有者，经销商
     */
    @NotBlank(message = "currentOwner不能为空")
    private String currentOwner;
    /**
     * 码的值
     */
    @NotBlank(message = "code不能为空")
    private String code;
}
