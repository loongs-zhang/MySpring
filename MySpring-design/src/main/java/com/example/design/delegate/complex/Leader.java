package com.example.design.delegate.complex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
public class Leader {

    private static Map<Worker.WorkType, Worker> map = new HashMap<>();

    static {
        BackendEmployee backendEmployee = new BackendEmployee();
        map.put(backendEmployee.goodAt(), backendEmployee);
        FrontEndEmployee frontEndEmployee = new FrontEndEmployee();
        map.put(frontEndEmployee.goodAt(), frontEndEmployee);
    }

    public void dispatch(Worker.WorkType workType) {
        Worker developer = map.get(workType);
        if (developer == null) {
            System.out.println("超出员工的能力范围");
            return;
        }
        developer.doWork();
    }

}
