package com.inventory.middle.domain.validator.annotation;

import java.lang.annotation.*;

import javax.validation.Payload;

import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;

/**
 * 物料凭证参数验证
 * <p>
 * 必须有 match nonMatch message 三个方法
 * 
 * @author kll
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface MaterialAdjustTypeValid {

    /**
     * 移动类型符合时校验 空所有都校验
     * 
     * @return
     */
    MaterialAdjustTypeEnum[] match() default {};

    /**
     * 移动类型不在其中时校验
     *
     * @return
     */
    MaterialAdjustTypeEnum[] nonMatch() default {};

    /**
     * 错误提示信息
     *
     * @return
     */
    String message() default "{MaterialAdjustTypeValid's value is invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
