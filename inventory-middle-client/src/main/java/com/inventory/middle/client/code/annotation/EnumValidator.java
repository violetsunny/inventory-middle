package com.inventory.middle.client.code.annotation;

import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-08-11 10:10:57
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.Validator.class)
public @interface EnumValidator {

    String message() default "{EnumValidator's value is invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String checkMethod() default "";

    /**
     * 当值为null时，是否判断为正确返回为true
     * @return
     */
    boolean trueIfNull() default false;

    class Validator implements ConstraintValidator<EnumValidator, Object> {

        private Class<? extends Enum<?>> enumClass;

        private String enumMethod;

        private boolean trueIfNull;


        @Override
        public void initialize(EnumValidator enumValue) {
            enumMethod = enumValue.checkMethod();
            enumClass = enumValue.enumClass();
            trueIfNull = enumValue.trueIfNull();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                if (trueIfNull) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }

            Class<?> valueClass = value.getClass();

            try {
                Method method;
                if (StringUtils.isEmpty(enumMethod)){
                    method = enumClass.getMethod(enumMethod);
                }else{
                    method = enumClass.getMethod(enumMethod, valueClass);
                }
                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                    return Boolean.FALSE;
                }

                if (!Modifier.isStatic(method.getModifiers())) {
                    return Boolean.FALSE;
                }

                Boolean result = (Boolean) method.invoke(null, value);
                return result == null ? false : result;
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

        }

    }
}
