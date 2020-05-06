package com.example.design.principle.lkp.follow;

import com.example.design.principle.lkp.before.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
public class Leader {
    public boolean getWorkResult() {
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            employeeList.add(new Employee());
        }
        for (Employee employee : employeeList) {
            employee.doWork();
        }
        return true;
    }
}
