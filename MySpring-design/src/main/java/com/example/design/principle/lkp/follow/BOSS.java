package com.example.design.principle.lkp.follow;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
@SuppressWarnings("unused")
public class BOSS {

    private Leader leader;

    public BOSS(Leader leader) {
        this.leader = leader;
    }

    public boolean getResult() {
        return leader.getWorkResult();
    }
}
