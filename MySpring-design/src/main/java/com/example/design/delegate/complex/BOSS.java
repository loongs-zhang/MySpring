package com.example.design.delegate.complex;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class BOSS {

    public void command(Worker.WorkType workType, Leader leader) {
        leader.dispatch(workType);
    }
}
