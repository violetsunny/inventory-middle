package com.inventory.middle.domain.plan.common.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 计划域 BO 基类
 * <p>
 * 本地替代 top.kdla.framework.biz.bo.BaseBo。
 * 在 macOS 上 BaseBo.java 与 BaseBO.java 为同一文件，故以此单一类兼容两个名字。
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Data
public abstract class BaseBo implements Serializable {

    private static final long serialVersionUID = -45091434594589996L;

    private String token;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 租户id
     */
    private String tenantId;

    public abstract String toLog();
}
