package com.example.design.chain.after;

import com.example.design.chain.before.ManagerLevel;
import com.example.design.chain.before.Request;
import com.example.design.chain.before.RequestType;

/**
 * @author SuccessZhang
 * @date 2020/05/19
 */
public class Boss extends AbstractManager {

    public Boss() {
        super(ManagerLevel.BOSS, null);
    }

    @Override
    public void handleRequest(Request request) {
        if (this.level.equals(ManagerLevel.BOSS)) {
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
