package com.inventory.middle.domain.model.bo.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 预警规则消息体
 * @author dongguo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorMessageBO implements Serializable {

    private static final long serialVersionUID = -3789880664052921517L;

    /**
     * 预警规则ID
     */
    private Long monitorRuleId;

    /**
     * beanName
     */
    private String beanName;

    /**
     * 预警规则是否改变了预警维度
     */
    private Boolean changedDimension;

    /**
     * 新增或者更新的预警规则行ID集合
     */
    private List<Long> ruleLineIds;

    /**
     * 删除的规则行ID
     */
    private List<Long> deletedRuleLineIds;

}
