package com.example.design.chain.before;

/**
 * @author SuccessZhang
 * @date 2020/05/18
 */
@SuppressWarnings("unused")
public class Manager {

    private final ManagerLevel level;

    public Manager(ManagerLevel level) {
        this.level = level;
    }

    public void handleRequest(Request request) {
        if (this.level.equals(ManagerLevel.MANAGER)) {
            if (request.getType().equals(RequestType.LEAVE_FOR_ONE_DAY)) {
                System.out.println("批准" + request.getEmployee().getName() + "请假1天");
            } else {
                System.out.println("抱歉，经理无权处理");
            }
        } else if (this.level.equals(ManagerLevel.CHIEF_INSPECTOR)) {
            if (request.getType().equals(RequestType.LEAVE_FOR_ONE_DAY)) {
                System.out.println("你去找你们经理吧");
            } else if (request.getType().equals(RequestType.LEAVE_FOR_SEVEN_DAY)) {
                System.out.println("批准" + request.getEmployee().getName() + "请假7天");
            } else {
                System.out.println("抱歉，总监无权处理");
            }
        } else if (this.level.equals(ManagerLevel.BOSS)) {
            if (request.getType().equals(RequestType.LEAVE_FOR_ONE_MONTH)) {
                System.out.println("批准" + request.getEmployee().getName() + "请假1个月");
            } else if (request.getType().equals(RequestType.RAISE)) {
                System.out.println("批准" + request.getEmployee().getName() + "升职加薪");
            } else {
                System.out.println("这种小事也要我来审批？");
            }
        }
    }
}
