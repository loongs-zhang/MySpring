package com.example.design.state.before;

/**
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class StateBeforeTest {
    public static void main(String[] args) {
        Order order = new Order();
        order.readyToPay();
        order.pay();
        order.readyToShip();
        order.ship();
        order.deliver();
        order.confirm();
        order.close();
    }
}
