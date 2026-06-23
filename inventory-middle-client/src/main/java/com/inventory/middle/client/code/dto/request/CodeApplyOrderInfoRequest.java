package com.inventory.middle.client.code.dto.request;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderInfoRequest implements Serializable {


    private static final long serialVersionUID = 6008581016530653993L;
    /**
     * 厂商id
     *
     * 厂商id和发起经销商id不能同时为空
     */
    private String ownerId;

    /**
     * 发起经销商id
     *
     * 厂商id和发起经销商id不能同时为空
     */
    private String inviterId;

    /**
     * 申请单id
     */
    @NotNull(message = "申请单id不能为空")
    private Long applyOrderId;




}
