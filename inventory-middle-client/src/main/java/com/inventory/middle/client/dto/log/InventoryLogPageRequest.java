package com.inventory.middle.client.dto.log;

import com.inventory.middle.client.enums.LogActionEnum;
import com.inventory.middle.client.enums.LogModuleEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 *
 * 注意： id、tenantId、requestId和operatorId 不能同时为空 ！！！！
 */
@Data
public class InventoryLogPageRequest implements Serializable {

    private static final long serialVersionUID = -7644694483614018863L;

    /**
     * 主键id
     *
     * id、tenantId、requestId和operatorId 不能同时为空
     */
    private Long id;

    /**
     * 租户id
     *
     * id、tenantId、requestId和operatorId 不能同时为空
     */
    private String tenantId;

    /**
     * 请求id 链路查询
     *
     * id、tenantId、requestId和operatorId 不能同时为空
     */
    private String requestId;

    /**
     * 日志模块
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
     *
     * id、tenantId、requestId和operatorId 不能同时为空
     */
    private String operatorId;

    @NotNull(message = "页面size不能为空")
    @Min(value = 1, message = "页面size最小为1")
    @Max(value = 500, message = "页面size最大为500")
    private Integer pageSize;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum;
}
