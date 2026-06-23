package com.inventory.middle.client.code.dto.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderDetailCreateRequest implements Serializable {


    private static final long serialVersionUID = -3417739126275642243L;

    @NotBlank(message = "流转码不能为空")
    @Size(max = 40, message = "流转码长度过长，最大40")
    private String code;

    @NotBlank(message = "内部码不能为空")
    @Size(max = 60, message = "内部码长度过长，最大60")
    private String innerCode;

    @NotBlank(message = "物料编码不能为空")
    @Size(max = 40, message = "物料编码过长，最大40")
    private String materialCode;

    @NotBlank(message = "物料名称不能为空")
    @Size(max = 120, message = "物料名称过长，最大120")
    private String materialName;

}
