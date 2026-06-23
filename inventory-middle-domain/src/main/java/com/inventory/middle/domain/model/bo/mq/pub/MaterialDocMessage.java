package com.inventory.middle.domain.model.bo.mq.pub;

import java.io.Serializable;

import com.inventory.middle.domain.model.enums.MaterialDocCategoryEnum;
import com.inventory.middle.domain.model.enums.MaterialDocChannelEnum;
import lombok.Data;

/**
 * @author dongguo.tao
 * @description 库存移动后 发送的消息体
 * @date 2021-08-30 14:04:11
 */

@Data
public class MaterialDocMessage implements Serializable {


    private static final long serialVersionUID = -3096419170189635773L;

    /**
     * 渠道 必须
     * @see MaterialDocChannelEnum
     */
    private Integer channel;

    /**
     * 租户id 必须
     */
    private String tenantId;

    /**
     * 出入库类型 出库 入库 出入库 取消  必须
     * @see MaterialDocCategoryEnum
     */
    private Integer materialDocCategory;

    /**
     * 物料凭证号 必须
     */
    private String materialDocNo;

    /**
     * 库存批次编号 非必须
     */
    private String batchNo;

    /**
     * 操作人 必须
     */
    private String operator;

    /**
     * 版本号：用于消费者唯一校验 非必须
     *
     * 场景：
     *      A系统发送消息M1给库存中心，库存中心入库后再发送消息M2，
     *      A系统消费M2时可以使用version校验M2是否为M1操作发送。
     */
    private String version;
}
