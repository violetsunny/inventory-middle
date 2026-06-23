package com.inventory.middle.infra.plan.aop;

import com.inventory.middle.infra.plan.aop.slot.ExternalMaterialFillingProcessSlot;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Plan ApplicationService 外部物料编码填充切面
 * <p>
 * 切点：application/plan/ 下所有方法，在方法返回后通过 SpEL 表达式将内部物料编码映射为外部物料编码。
 * 具体填充规则由 {@link com.inventory.middle.infra.plan.aop.extension.ExternalMaterialFillingProcessor} 实现类决定。
 *
 * @author Danny.Lee
 */
@Aspect
@Component
public class ServiceHandlerAspect {

    private static final SlotProcessChain CHAIN;

    static {
        CHAIN = new SlotProcessChain();
        CHAIN.addLast(new ExternalMaterialFillingProcessSlot());
    }

    @Pointcut("execution(* com.inventory.middle.application.plan..*.*(..))")
    public void planApplicationService() {
    }

    @Around("planApplicationService()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Invocation invocation = buildInvocation(joinPoint);
        try {
            invocation.setResult(joinPoint.proceed());
        } finally {
            CHAIN.exit(invocation);
        }
        return invocation.getResult();
    }

    private Invocation buildInvocation(ProceedingJoinPoint point) {
        Invocation invocation = new Invocation();
        invocation.setParameters(point.getArgs());
        invocation.setClassSpec(point.getTarget().getClass());
        invocation.setMethodSpec(((MethodSignature) point.getSignature()).getMethod());
        return invocation;
    }
}
