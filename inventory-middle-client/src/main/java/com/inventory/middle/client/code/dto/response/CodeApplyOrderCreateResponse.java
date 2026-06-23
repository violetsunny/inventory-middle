package com.inventory.middle.client.code.dto.response;


import lombok.Data;

import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderCreateResponse implements Serializable {


    private static final long serialVersionUID = -8490324807312376403L;

    /**
     * 申请单id
     */
    private Long id;

    /**
     * 申请单编码
     */
    private String orderNo;

}
