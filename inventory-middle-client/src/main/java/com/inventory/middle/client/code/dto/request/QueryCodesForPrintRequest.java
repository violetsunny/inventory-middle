package com.inventory.middle.client.code.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjs
 * @date 2021/12/23
 */
@Data
public class QueryCodesForPrintRequest implements Serializable {

    /**
     * 发布者
     */
    @NotBlank(message = "publisher不能为空")
    private String publisher;

    /**
     * 内部码列表
     */
    @NotEmpty(message = "innerCodeList不能为空")
    private List<String> innerCodeList;

    /**
     * 操作人
     */
    @NotBlank(message = "operatorId不能为空")
    private String operatorId;

}
