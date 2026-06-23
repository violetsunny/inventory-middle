package com.inventory.middle.client.dto.log;

import com.inventory.middle.client.enums.LogActionEnum;
import com.inventory.middle.client.enums.LogModuleEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 */
@Data
public class InventoryLogCreateRequest implements Serializable {

    private static final long serialVersionUID = 1708428435167393275L;

    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    @NotBlank(message = "请求id不能为空")
    private String requestId;

    /**
     * 日志模块
     * @see LogModuleEnum
     */
    @NotNull(message = "日志模块不能为空")
    private Integer logModule;

    /**
     * 日志行为
     * @see LogActionEnum
     */
    @NotNull(message = "日志行为不能为空")
    private Integer action;

    @NotBlank(message = "日志内容不能为空")
    private String payload;

    @NotNull(message = "日志上报时间不能为空")
    private Date reportTime;

    @NotNull(message = "操作人id不能为空")
    private String operatorId;

}
