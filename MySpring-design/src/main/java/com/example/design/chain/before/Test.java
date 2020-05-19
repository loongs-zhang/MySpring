package com.example.design.chain.before;

/**
 * @author SuccessZhang
 * @date 2020/05/18
 */
public class Test {
    public static void main(String[] args) {
        Manager manager = new Manager(ManagerLevel.MANAGER);
        Manager chiefInspector = new Manager(ManagerLevel.CHIEF_INSPECTOR);
        Manager boss = new Manager(ManagerLevel.BOSS);
        Employee employee = new Employee("小张");

        manager.handleRequest(employee.applyLeaveForOneDay());
        manager.handleRequest(employee.applyLeaveForSevenDay());
        manager.handleRequest(employee.applyLeaveForOneMonth());
        manager.handleRequest(employee.applyRaise());

        System.out.println();
        chiefInspector.handleRequest(employee.applyLeaveForOneDay());
        chiefInspector.handleRequest(employee.applyLeaveForSevenDay());
        chiefInspector.handleRequest(employee.applyLeaveForOneMonth());
        chiefInspector.handleRequest(employee.applyRaise());

        System.out.println();
        boss.handleRequest(employee.applyLeaveForOneDay());
        boss.handleRequest(employee.applyLeaveForSevenDay());
        boss.handleRequest(employee.applyLeaveForOneMonth());
        boss.handleRequest(employee.applyRaise());
    }
}
