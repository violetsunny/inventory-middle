package com.inventory.middle.client.dto.log;

import com.inventory.middle.client.enums.LogActionEnum;
import com.inventory.middle.client.enums.LogModuleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryLogDTO implements Serializable {


    private static final long serialVersionUID = -281668590921113098L;
    /**
     * id
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
     * 日志模块 0-默认；1-物理仓；2-逻辑仓；3-预警；4-入库；5-出库；6-出入库；7、取消；8-窜码
     * @see LogModuleEnum
     */
    private Integer logModule;

    /**
     * 日志行为：0-默认；1-新增；2-修改；3-删除；
     * @see LogActionEnum
     */
    private Integer action;

    /**
     * 日志内容
     */
    private String payload;

    /**
     * 日志上报时间
     */
    private Date reportTime;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建时间
     */
    private Date createTime;


}
