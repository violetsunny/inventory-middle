package com.inventory.middle.domain.model.bo.monitor;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.inventory.middle.domain.model.bo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dongguo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryMonitorRuleLineBO extends BaseBO implements Serializable {

    private static final long serialVersionUID = -6816759039761175960L;

    /**
     * 预警规则id
     */
    private Long monitorRuleId;

    /**
     * 预警维度
     */
    private String monitorDimension;

    /**
     * 预警对象
     * 当预警维度为指定物料的时候，预警对象为物料编码
     * 当预警维度为逻辑仓时，预警对象为逻辑仓ID
     */
    private String monitorObject;

    /**
     * 上限
     */
    private BigDecimal monitorCeil;

    /**
     * 下限
     */
    private BigDecimal monitorFloor;

    public static void main(String[] args) {
        //下次年检时间
        DateTime annualDate = DateUtil.beginOfDay(DateUtil.parseDateTime("2022-05-03 20:40:56"));
        //当前时间
        DateTime currentDate = DateUtil.beginOfDay(new Date());

        long between = DateUtil.between(currentDate, annualDate, DateUnit.DAY, false);
        System.out.println(">>>>>"+between);
        System.out.println(DateUtil.offsetDay(annualDate, 3));

    }

}
