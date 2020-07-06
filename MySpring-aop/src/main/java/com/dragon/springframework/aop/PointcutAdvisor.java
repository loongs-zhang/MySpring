package com.dragon.springframework.aop;

/**
 * 由切入点驱动的所有Advisor的超级接口。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * 获取驱动该Advisor的切入点。
     */
    Pointcut getPointcut();

}
