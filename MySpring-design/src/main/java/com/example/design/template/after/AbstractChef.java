package com.example.design.template.after;

import com.example.design.template.before.Chef;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
public abstract class AbstractChef implements Chef {
    @Override
    public final void cook() {
        //1.买菜
        this.buyFood();
        //2.洗菜
        this.washFood();
        //3.切菜
        this.cutUpFood();
        //4.生火
        this.lightFire();
        //5.炒菜
        this.stirFry();
        //6.装盘
        this.putOnPlate();
        //7.上桌
        this.putOnTable();
    }

    /**
     * 炒不同的菜需要买不同的菜
     */
    protected abstract void buyFood();

    /**
     * 不同的菜洗法不一样
     */
    protected abstract void washFood();

    /**
     * 不同的菜切法不一样
     */
    protected abstract void cutUpFood();

    private void lightFire() {
        System.out.println("生火完毕");
    }

    /**
     * 不同的菜炒法不一样
     */
    protected abstract void stirFry();

    /**
     * 不同大厨的摆盘方式不一样
     */
    protected abstract void putOnPlate();

    private void putOnTable() {
        System.out.println("菜炒好了，也装好盘了，上桌");
    }
}
