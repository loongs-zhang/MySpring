package com.example.design.facade.after;

import com.example.design.facade.before.FinancialSystem;
import com.example.design.facade.before.LogisticsSystem;
import com.example.design.facade.before.OrderSystem;

/**
 * @author SuccessZhang
 * @date 2020/06/03
 */
public class ElectronicCommerceSystem {

    private OrderSystem orderSystem = new OrderSystem();

    private FinancialSystem financialSystem = new FinancialSystem();

    private LogisticsSystem logisticsSystem = new LogisticsSystem();

    public void submit() {
        orderSystem.submit();
    }

    public void pay() {
        financialSystem.pay();
    }

    public void ship() {
        logisticsSystem.ship();
    }

    public void confirm() {
        logisticsSystem.confirm();
    }
}
