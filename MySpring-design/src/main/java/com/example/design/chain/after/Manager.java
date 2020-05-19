package com.example.design.chain.after;

import com.example.design.chain.before.ManagerLevel;
import com.example.design.chain.before.Request;
import com.example.design.chain.before.RequestType;

/**
 * @author SuccessZhang
 * @date 2020/05/19
 */
public class Manager extends AbstractManager {

    public Manager(AbstractManager successor) {
        super(ManagerLevel.MANAGER, successor);
    }

    @Override
    public void handleRequest(Request request) {
        if (this.level.equals(ManagerLevel.MANAGER)) {
            if (request.getType().equals(RequestType.LEAVE_FOR_ONE_DAY)) {
                System.out.println("批准" + request.getEmployee().getName() + "请假1天");
            } else {
                System.out.println("抱歉，经理无权处理");
                successor.handleRequest(request);
            }
        }
    }
}
