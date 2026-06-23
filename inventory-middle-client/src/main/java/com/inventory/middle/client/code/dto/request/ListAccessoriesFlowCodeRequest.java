package com.inventory.middle.client.code.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询备件流转码列表
 *
 * @author hjs
 * @date 2021/12/15
 */
@Data
public class ListAccessoriesFlowCodeRequest implements Serializable {

    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 发布人
     */
    private String publisher;
    /**
     * 来源生成编号
     */
    private String sourceNo;
    /**
     * 内部码列表
     */
    private List<String> innerCodeList;

}
