package com.example.design.chain.after;

import com.example.design.chain.before.Employee;

/**
 * @author SuccessZhang
 * @date 2020/05/19
 */
public class ChainTest {
    public static void main(String[] args) {
        Boss boss = new Boss();
        ChiefInspector chiefInspector = new ChiefInspector(boss);
        Manager manager = new Manager(chiefInspector);
        Employee employee = new Employee("小张");

        manager.handleRequest(employee.applyLeaveForOneDay());
        manager.handleRequest(employee.applyLeaveForSevenDay());
        manager.handleRequest(employee.applyLeaveForOneMonth());
        manager.handleRequest(employee.applyRaise());
        System.out.print("\n");

        chiefInspector.handleRequest(employee.applyLeaveForOneDay());
        chiefInspector.handleRequest(employee.applyLeaveForSevenDay());
        chiefInspector.handleRequest(employee.applyLeaveForOneMonth());
        chiefInspector.handleRequest(employee.applyRaise());
        System.out.print("\n");

        boss.handleRequest(employee.applyLeaveForOneDay());
        boss.handleRequest(employee.applyLeaveForSevenDay());
        boss.handleRequest(employee.applyLeaveForOneMonth());
        boss.handleRequest(employee.applyRaise());
    }
}
