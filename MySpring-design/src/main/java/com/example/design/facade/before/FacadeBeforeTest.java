package com.example.design.facade.before;

/**
 * @author SuccessZhang
 * @date 2020/06/03
 */
public class FacadeBeforeTest {
    public static void main(String[] args) {
        OrderSystem orderSystem = new OrderSystem();
        FinancialSystem financialSystem = new FinancialSystem();
        LogisticsSystem logisticsSystem = new LogisticsSystem();

        orderSystem.submit();
        financialSystem.pay();
        logisticsSystem.ship();
        logisticsSystem.confirm();
    }
}
