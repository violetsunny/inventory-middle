package com.inventory.middle.domain.model.bo.mq.pub;

import java.io.Serializable;

import lombok.Data;

/**
 * @author dongguo.tao
 * @description 创建入库凭证后 发送的消息体。数能 使用。
 * @date 2021-08-30 14:04:11
 */

@Data
public class SnMaterialDocInMessage extends MaterialDocMessage implements Serializable {

    private static final long serialVersionUID = -6325160885214247383L;

    /**
     * 同一个Excel导入序列号
     */
    private String batchNumber;

    /**
     * Excel中的序号
     */
    private Integer batchIndex;

}
