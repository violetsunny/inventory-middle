package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hjs
 * @date 2021/12/14
 */

/**
 * 查询code表的参数PO对象
 */
@Data
public class ListCodeParamPO {

    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 产生码记录的源单号
     */
    private String sourceNo;
    /**
     * 内部唯一码
     */
    private String innerCode;
    /**
     * 码记录的类型
     */
    private String type;
    /**
     * 码记录的值
     */
    private String code;
    /**
     * 码记录列表
     */
    private List<String> codeList;

    /**
     * 内部码列表
     */
    private List<String> innerCodeList;
    /**
     * 码记录的发布者
     */
    private String publisher;
    /**
     * 码记录的前一任持有者
     */
    private String preOwner;
    /**
     * 码记录的当前持有者
     */
    private String currentOwner;
    /**
     * 码记录的状态
     */
    private String status;

    /**
     * 被排除的状态
     */
    private List<String> excludeStatusList;
    /**
     * 码记录的扩展字段
     */
    private String extendField1;
    /**
     * 码记录的扩展字段
     */
    private String extendField2;
    /**
     * 码记录的扩展字段
     */
    private String fuzzyExtendField2;

    /**
     * 删除标识
     */
    private Long deleted;

    /**
     * 生效日期，起始
     */
    private Date activeTimeStart;
    /**
     * 生效日期，终止
     */
    private Date activeTimeEnd;
}
