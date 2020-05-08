package com.example.design.delegate.complex;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public interface Worker {

    WorkType goodAt();

    void doWork();

    public enum WorkType {
        //后端
        backend,
        //前端
        frontEnd,
        //未知
        unknown
    }
}
