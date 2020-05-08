package com.example.design.delegate.complex;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class FrontEndEmployee implements Worker {

    @Override
    public WorkType goodAt() {
        return WorkType.frontEnd;
    }

    @Override
    public void doWork() {
        System.out.println("front-end work done");
    }
}
