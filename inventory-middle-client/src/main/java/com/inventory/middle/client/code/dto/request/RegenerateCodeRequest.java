package com.inventory.middle.client.code.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 重新生成 码 的请求
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class RegenerateCodeRequest implements Serializable {

    /**
     * 来源系统
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;
    /**
     * 发布者
     */
    private String publisher;


    /**
     * 内部码
     */
    @NotBlank(message = "innerCode不能为空")
    private String innerCode;

    /**
     * 操作人
     */
    @NotBlank(message = "operatorId不能为空")
    private String operatorId;

    @NotBlank(message = "来源系统不能为空")
    @Size(max = 20, message = "来源系统不能过长")
    private String sourceSystem;

}
