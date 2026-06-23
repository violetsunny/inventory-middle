package com.inventory.middle.client.code.dto.request;

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
public class QueryCodeDetailByInnerCodeRequest implements Serializable {

    /**
     * AppKey
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;

    /**
     * 发布者
     */
    @NotBlank(message = "publisher不能为空")
    private String publisher;
    /**
     * 内部码的值
     */
    @NotBlank(message = "innerCode不能为空")
    private String innerCode;
}
