package com.inventory.middle.domain.model.bo.monitor;

import com.inventory.middle.domain.model.bo.BaseBO;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 库存报警通知记录
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class InventoryAlertNotificationBO extends BaseBO implements Serializable {

    private static final long serialVersionUID = 7185386796368384490L;

    /**
     * 报警日志id
     */
    private Long alertId;

    /**
     * 通知方式
     */
    private String notificationMode;

    /**
     * 通知地址（手机/邮箱）
     */
    private String notificationAddress;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知url地址
     */
    private String url;

    /**
     * 通知状态（是否发送）
     */
    private int status;




}
