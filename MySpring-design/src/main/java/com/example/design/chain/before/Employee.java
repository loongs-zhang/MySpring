package com.example.design.chain.before;

import lombok.Data;

/**
 * @author SuccessZhang
 */
@SuppressWarnings("unused")
@Data
public class Employee {

    private final String name;

    public Employee(String name) {
        this.name = name;
    }

    public Request applyLeaveForOneDay() {
        return new Request(this, RequestType.LEAVE_FOR_ONE_DAY);
    }

    public Request applyLeaveForSevenDay() {
        return new Request(this, RequestType.LEAVE_FOR_SEVEN_DAY);
    }

    public Request applyLeaveForOneMonth() {
        return new Request(this, RequestType.LEAVE_FOR_ONE_MONTH);
    }

    public Request applyRaise() {
        return new Request(this, RequestType.RAISE);
    }
}
