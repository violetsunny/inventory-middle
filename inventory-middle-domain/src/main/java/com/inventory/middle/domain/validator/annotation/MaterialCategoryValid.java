package com.inventory.middle.domain.validator.annotation;

import java.lang.annotation.*;

import javax.validation.Payload;

import com.inventory.middle.domain.model.enums.MaterialDocCategoryEnum;

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
public @interface MaterialCategoryValid {

    /**
     * 移动类型符合时校验 空所有都校验
     * 
     * @return
     */
    MaterialDocCategoryEnum[] match() default {};

    /**
     * 移动类型不在其中时校验
     *
     * @return
     */
    MaterialDocCategoryEnum[] nonMatch() default {};

    /**
     * 错误提示信息
     *
     * @return
     */
    String message() default "{MaterialCategoryValid's value is invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
