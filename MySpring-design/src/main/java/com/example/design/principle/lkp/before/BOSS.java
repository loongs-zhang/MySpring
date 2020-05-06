package com.example.design.principle.lkp.before;

import java.util.ArrayList;
import java.util.List;

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
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            employeeList.add(new Employee());
        }
        return leader.getWorkResult(employeeList);
    }
}
