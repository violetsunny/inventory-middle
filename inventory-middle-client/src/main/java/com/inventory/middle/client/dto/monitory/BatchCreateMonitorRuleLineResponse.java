package com.inventory.middle.client.dto.monitory;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 库存预警规则行
 * @author dongguo.tao
 * @date 2021-06-16
 */

@Data
public class BatchCreateMonitorRuleLineResponse implements Serializable {

    private static final long serialVersionUID = 3301482049134022436L;

    /**
     * 成功数量
     */
    private Integer successNum;

    /**
     * 失败数量
     */
    private Integer failedNum ;

    /**
     * 失败预警规则行
     */
    private List<FailedCreateMonitorRuleLineDTO> failedList;
}
