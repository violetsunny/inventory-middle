package com.inventory.middle.domain.common.exception;

import java.lang.annotation.*;

/**
 * 异常包装
 * @author kanglele01
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionWrapper {


}
