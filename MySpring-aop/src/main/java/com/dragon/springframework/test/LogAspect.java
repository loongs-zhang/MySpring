package com.dragon.springframework.test;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import com.dragon.springframework.aop.aspectj.annotation.After;
import com.dragon.springframework.aop.aspectj.annotation.AfterReturning;
import com.dragon.springframework.aop.aspectj.annotation.AfterThrowing;
import com.dragon.springframework.aop.aspectj.annotation.Around;
import com.dragon.springframework.aop.aspectj.annotation.Aspect;
import com.dragon.springframework.aop.aspectj.annotation.Before;
import com.dragon.springframework.aop.aspectj.annotation.Pointcut;
import com.dragon.springframework.aop.framework.ReflectiveMethodInvocation;
import com.dragon.springframework.aop.intercept.JoinPoint;
import com.dragon.springframework.aop.intercept.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * Advice通知类
 * 测试after,before,around,throwing,returning Advice.
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
@SuppressWarnings("unused")
@Aspect
public class LogAspect {

    @Pointcut("execution(* com.dragon.springframework.test..*.*(..))")
    public void pointcut() {
    }

    /**
     * 在核心业务执行前执行，不能阻止核心业务的调用。
     */
    @Before("pointcut()")
    private void doBefore(JoinPoint joinPoint) {
        System.out.println("-----doBefore().invoke-----");
        ReflectiveMethodInvocation methodInvocation = (ReflectiveMethodInvocation) joinPoint;
        methodInvocation.setUserAttribute("test", "test1");
        System.out.println("-----End of doBefore()------");
    }

    /**
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * 注意：当核心业务抛异常后，立即退出，转向After Advice
     * 执行完毕After Advice，再转到Throwing Advice
     */
    @Around("pointcut()")
    private Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("-----doAround().invoke-----");

        MethodInvocationProceedingJoinPoint joinPoint = (MethodInvocationProceedingJoinPoint) proceedingJoinPoint;
        ProxyMethodInvocation methodInvocation = joinPoint.getMethodInvocation();
        System.out.println("Around getUserAttribute->" + methodInvocation.getUserAttribute("test"));
        methodInvocation.setUserAttribute("test", "test2");
        Object[] args = proceedingJoinPoint.getArgs();
        System.out.println("args->" + Arrays.toString(args));
        //调用核心逻辑
        Object retVal = proceedingJoinPoint.proceed(args);

        System.out.println("-----End of doAround()------");
        return retVal;
    }

    /**
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
     */
    @After("pointcut()")
    private void doAfter(JoinPoint joinPoint) {
        System.out.println("-----doAfter().invoke-----");
        ReflectiveMethodInvocation methodInvocation = (ReflectiveMethodInvocation) joinPoint;
        System.out.println("After getUserAttribute->" + methodInvocation.getUserAttribute("test"));
        System.out.println("-----End of doAfter()------");
    }

    /**
     * 核心业务逻辑调用正常退出后，不管是否有返回值，
     * 只要正常退出，都会执行此Advice
     */
    @AfterReturning(value = "pointcut()", returning = "returnValue")
    private void doReturn(JoinPoint joinPoint, Object returnValue) {
        System.out.println("-----doReturn().invoke-----");
        System.out.println(" 返回值：" + returnValue);
        System.out.println("-----End of doReturn()------");
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     */
    @AfterThrowing(value = "pointcut()", throwing = "throwable")
    private void doThrowing(JoinPoint joinPoint, Throwable throwable) {
        System.out.println("-----doThrowing().invoke-----");
        System.out.println(" 错误信息：" + throwable.getMessage());
        System.out.println("-----End of doThrowing()------");
    }
}