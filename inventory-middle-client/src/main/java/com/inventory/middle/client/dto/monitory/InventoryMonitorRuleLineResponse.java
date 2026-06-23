package com.inventory.middle.client.dto.monitory;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 库存预警规则行
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class InventoryMonitorRuleLineResponse extends InventoryMonitorRuleLineInfoDTO implements Serializable {

    private static final long serialVersionUID = 7996424701122117864L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private String creatorId;


    /**
     * 更新人ID
     */
    private String updatorId;

    /**
     * 删除状态
     */
    private Integer deleted;
}
