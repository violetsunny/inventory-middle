package com.inventory.middle.client.code.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询未使用状态的备件流转码列表
 *
 * @author hjs
 * @date 2021/12/15
 */
@Data
public class ListUnUsedAccessoriesFlowCodeRequest implements Serializable {


    /**
     * 内部码列表
     */
    private List<String> innerCodeList;

}
