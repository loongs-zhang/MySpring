package com.example.design.facade.after;

/**
 * @author SuccessZhang
 * @date 2020/06/03
 */
public class FacadeAfterTest {
    public static void main(String[] args) {
        ElectronicCommerceSystem system = new ElectronicCommerceSystem();
        system.submit();
        system.pay();
        system.ship();
        system.confirm();
    }
}
