package com.example.design.chain.before;

import lombok.Data;

/**
 * @author SuccessZhang
 */
@SuppressWarnings("unused")
@Data
public class Request {

    private final Employee employee;

    private final RequestType type;

    public Request(Employee employee, RequestType type) {
        this.employee = employee;
        this.type = type;
    }
}
