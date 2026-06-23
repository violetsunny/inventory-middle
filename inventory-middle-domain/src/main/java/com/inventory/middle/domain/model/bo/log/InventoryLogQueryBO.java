package com.inventory.middle.domain.model.bo.log;

import com.inventory.middle.domain.model.enums.LogActionEnum;
import com.inventory.middle.domain.model.enums.LogModuleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryLogQueryBO implements Serializable {

    private static final long serialVersionUID = 5172807798181929682L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 请求id 链路查询
     */
    private String requestId;

    /**
     * 日志模块 0-默认；1-物理仓；2-逻辑仓；3-预警；4-入库；5-出库；6-出入库；6、窜码
     * @see LogModuleEnum
     */
    private Integer logModule;

    /**
     * 日志行为：0-默认；1-新增；2-修改；3-删除；
     * @see LogActionEnum
     */
    private List<Integer> actionList;

    /**
     * 日志上报查询开始时间
     */
    private Date beginTime;

    /**
     * 日志上报查询结束时间
     */
    private Date endTime;

    /**
     * 操作人id
     */
    private String operatorId;


}
