package com.inventory.middle.client.code.dto.request;

import com.inventory.middle.client.code.annotation.EnumValidator;
import com.inventory.middle.client.code.enums.BusinessNoEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 分页查询 码 的请求
 *
 * @author hjs
 * @date 2021/12/16
 */
@Data
public class PageQueryManufacturerCodeRequest implements Serializable {


    /**
     * 来源系统
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;
    /**
     * 业务编号
     */
    @EnumValidator(enumClass = BusinessNoEnum.class, checkMethod = "isValidCode", message = "不合法的businessNo值")
    @NotBlank(message = "businessNo不能为空")
    private String businessNo;

    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    /**
     * 码记录的发布者
     */
    @NotBlank(message = "publisher不能为空")
    private String publisher;
    /**
     * 经销商
     */
    private String currentOwner;

    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 模糊查询物料名称
     */
    private String fuzzyMaterialName;
    /**
     * 备件流转码
     */
    private String code;

    /**
     * 创建日期，起始
     */
    private Date activeTimeStart;
    /**
     * 创建日期，终点
     */
    private Date activeTimeEnd;

    /**
     * 状态
     */
    private String status;



}
