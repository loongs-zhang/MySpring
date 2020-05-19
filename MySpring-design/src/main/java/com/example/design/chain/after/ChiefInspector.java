package com.example.design.chain.after;

import com.example.design.chain.before.ManagerLevel;
import com.example.design.chain.before.Request;
import com.example.design.chain.before.RequestType;

/**
 * @author SuccessZhang
 * @date 2020/05/19
 */
public class ChiefInspector extends AbstractManager {

    public ChiefInspector(AbstractManager successor) {
        super(ManagerLevel.CHIEF_INSPECTOR, successor);
    }

    @Override
    public void handleRequest(Request request) {
        if (this.level.equals(ManagerLevel.CHIEF_INSPECTOR)) {
            if (request.getType().equals(RequestType.LEAVE_FOR_ONE_DAY)) {
                System.out.println("你去找你们经理吧");
            } else if (request.getType().equals(RequestType.LEAVE_FOR_SEVEN_DAY)) {
                System.out.println("批准" + request.getEmployee().getName() + "请假7天");
            } else {
                System.out.println("抱歉，总监无权处理");
                successor.handleRequest(request);
            }
        }
    }
}
