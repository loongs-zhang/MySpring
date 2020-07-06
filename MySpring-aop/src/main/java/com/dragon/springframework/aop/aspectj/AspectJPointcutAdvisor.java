package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.Advice;
import com.dragon.springframework.aop.Pointcut;
import com.dragon.springframework.aop.PointcutAdvisor;
import com.dragon.springframework.core.Ordered;
import lombok.Setter;

/**
 * 使{@link AbstractAspectJAdvice}适配为{@link PointcutAdvisor}接口。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class AspectJPointcutAdvisor implements PointcutAdvisor, Ordered {

    private final AbstractAspectJAdvice advice;

    private final Pointcut pointcut;

    @Setter
    private Integer order;

    public AspectJPointcutAdvisor(AbstractAspectJAdvice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public int getOrder() {
        if (this.order != null) {
            return this.order;
        } else {
            return this.advice.getOrder();
        }
    }
}
