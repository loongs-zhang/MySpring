package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Advice通知类
 * 测试after,before,around,throwing,returning Advice.
 *
 * @author SuccessZhang
 * @date 020/04/27
 */
@SuppressWarnings("unused")
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com..*(java.lang.String))")
    public void pointcut() {
    }

    /**
     * 在核心业务执行前执行，不能阻止核心业务的调用。
     */
    @Before(value = "pointcut()", argNames = "joinPoint")
    private void doBefore(JoinPoint joinPoint) {
        System.out.println("-----doBefore().invoke-----");
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
        System.out.println("-----End of doAfter()------");
    }

    /**
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     */
    @AfterReturning(pointcut = "pointcut()", returning = "returnValue")
    private void doReturn(JoinPoint joinPoint, Object returnValue) {
        System.out.println("-----doReturn().invoke-----");
        System.out.println(" 返回值：" + returnValue);
        System.out.println("-----End of doReturn()------");
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    private void doThrowing(JoinPoint joinPoint, Throwable throwable) {
        System.out.println("-----doThrowing().invoke-----");
        System.out.println(" 错误信息：" + throwable.getMessage());
        System.out.println("-----End of doThrowing()------");
    }
}