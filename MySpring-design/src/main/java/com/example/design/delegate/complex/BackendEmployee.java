package com.example.design.delegate.complex;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class BackendEmployee implements Worker {

    @Override
    public WorkType goodAt() {
        return WorkType.backend;
    }

    @Override
    public void doWork() {
        System.out.println("backend work done");
    }
}
