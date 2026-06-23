package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author caosheng
 * @version 1.0
 * @description: 计划参数
 * @date 2021/10/18 16:03
 */
@Data
@EqualsAndHashCode
public class PlanParamVO implements Serializable {

    private static final long serialVersionUID = 4560238914709248L;


    /**
     * 枚举编码
     */
    private String code;

    /**
     * 枚举描述
     */
    private String desc;

    public static PlanParamVO of(String code, String desc) {
        PlanParamVO planParam = new PlanParamVO();
        planParam.setCode(code);
        planParam.setDesc(desc);
        return planParam;
    }

}