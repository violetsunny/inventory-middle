/**
 * kll Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

import java.io.Serializable;

import lombok.Data;

/**
 * @author kll
 * @version $Id: MaterialDocNoResDTO, v 0.1 2021/6/18 9:55 Exp $
 */
@Data
public class MaterialDocMsgResDTO implements Serializable {
    /**
     * 是否成功
     */
    private Boolean result;
    /**
     * 其他信息
     */
    private String desc;
}
