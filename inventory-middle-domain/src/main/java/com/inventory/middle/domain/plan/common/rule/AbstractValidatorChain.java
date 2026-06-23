package com.inventory.middle.domain.plan.common.rule;

import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象校验器链基类
 *
 * @author peisheng.wang (migrated)
 * @date 2021/9/27
 */
public abstract class AbstractValidatorChain implements IValidatorChain, InitializingBean {

    protected List<IValidator> validators = new ArrayList<>();

    /**
     * 初始化校验器链（子类实现，将校验器按序加入 validators）
     */
    protected abstract void initChain();

    @Override
    public void afterPropertiesSet() {
        initChain();
    }
}
