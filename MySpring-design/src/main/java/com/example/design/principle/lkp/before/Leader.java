package com.example.design.principle.lkp.before;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
public class Leader {
    public boolean getWorkResult(List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            employee.doWork();
        }
        return true;
    }
}
