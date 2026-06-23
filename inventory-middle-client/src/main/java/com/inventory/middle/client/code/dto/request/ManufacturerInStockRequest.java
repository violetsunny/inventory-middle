package com.inventory.middle.client.code.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.BusinessNoEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 批量创建备件流转码请求
 *
 * @author hjs
 * @date 2021/12/14
 */
@Data
public class ManufacturerInStockRequest implements Serializable {

    @NotBlank(message = "appKey不能为空")
    private String appKey;

    /**
     * 业务编号
     */
    @EnumValidator(enumClass = BusinessNoEnum.class, checkMethod = "isValidCode", message = "不合法的businessNo值")
    @NotBlank(message = "businessNo不能为空")
    private String businessNo;

    @NotBlank(message = "操作人不能为空")
    private String operator;

    @NotBlank(message = "来源系统不能为空")
    @Size(max = 20, message = "来源系统不能过长")
    private String sourceSystem;

    @NotEmpty(message = "itemRequestList不能为空")
    @Valid
    private List<BatchCreateCodeItemRequest> itemRequestList;

}
