package com.inventory.middle.client.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 基础请求对象
 *
 * @author vincent.li
 * @date 2021/10/13
 */
@Data
public class BaseRequest implements Serializable {
    /**
     * 调用接口应用服务标识，必填
     */
    //@NotEmpty(message = "appKey 不能为空")
    private String appKey;
}
