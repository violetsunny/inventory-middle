package com.inventory.middle.domain.model.bo.log;

import com.inventory.middle.domain.common.annotation.EnumValidator;
import com.inventory.middle.domain.model.enums.LogActionEnum;
import com.inventory.middle.domain.model.enums.LogModuleEnum;
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
public class InventoryLogBO implements Serializable {

    private static final long serialVersionUID = 5172807798181929682L;

    /**
     * id
     */
    private Long id;

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    /**
     * 请求id 链路查询
     */
    @NotBlank(message = "请求id不能为空")
    private String requestId;

    /**
     * 日志模块 0-默认；1-物理仓；2-逻辑仓；3-预警；4-入库；5-出库；6-出入库；6、窜码
     */
    @NotNull(message = "日志模块不能为空")
    @EnumValidator(enumClass = LogModuleEnum.class, checkMethod = "checkByCode", message = "日志模块值错误")
    private Integer logModule;

    /**
     * 日志行为：0-默认；1-新增；2-修改；3-删除；
     */
    @NotNull(message = "日志行为不能为空")
    @EnumValidator(enumClass = LogActionEnum.class, checkMethod = "checkByCode", message = "日志行为值错误")
    private Integer action;

    /**
     * 日志内容
     */
    @NotBlank(message = "日志内容不能为空")
    private String payload;

    /**
     * 日志上报时间
     */
    @NotNull(message = "日志上报时间不能为空")
    private Date reportTime;

    /**
     * 创建人id
     */
    @NotNull(message = "日志创建人id不能为空")
    private String creatorId;

    /**
     * 创建时间
     */
    private Date createTime;


}
