package com.inventory.middle.client.code.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 窜货申请校验结果
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class FleeingGoodsApplyCheckResponse implements Serializable {

    /**
     * 是否通过校验
     */
    private boolean success;
    /**
     * 校验的备件流转码
     */
    private String code;
    /**
     * 校验后的结果码
     */
    private String resultCode;
    /**
     * 校验后的结果信息
     */
    private String resultMessage;
}
