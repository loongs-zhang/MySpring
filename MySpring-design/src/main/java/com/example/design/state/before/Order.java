package com.example.design.state.before;

/**
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class Order {

    private State state;

    public Order() {
        this.state = State.SUBMITTED;
    }

    public void readyToPay() {
        System.out.println(this.state);
        if (this.state.equals(State.SUBMITTED)) {
            this.state = State.UNPAID;
        }
    }

    public void pay() {
        System.out.println(this.state);
        if (this.state.equals(State.UNPAID)) {
            this.state = State.PAID;
        }
    }

    public void readyToShip() {
        System.out.println(this.state);
        if (this.state.equals(State.PAID)) {
            this.state = State.NOT_SHIPPED;
        }
    }

    public void ship() {
        System.out.println(this.state);
        if (this.state.equals(State.NOT_SHIPPED)) {
            this.state = State.SHIPPED;
        }
    }

    public void deliver() {
        System.out.println(this.state);
        if (this.state.equals(State.SHIPPED)) {
            this.state = State.DELIVERING;
        }
    }

    public void confirm() {
        System.out.println(this.state);
        if (this.state.equals(State.DELIVERING)) {
            this.state = State.CONFIRMED;
        }
    }

    public void close() {
        System.out.println(this.state);
        if (this.state.equals(State.CONFIRMED)) {
            this.state = State.CLOSED;
        }
    }

}
