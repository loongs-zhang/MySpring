package com.example.design.template.before;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
@SuppressWarnings("unused")
public class CabbageChef implements Chef {

    @Override
    public void cook() {
        System.out.println("买大白菜");
        System.out.println("洗大白菜");
        System.out.println("切大白菜");
        System.out.println("生火完毕");
        System.out.println("一号大厨的大白菜炒法");
        System.out.println("一号大厨的摆盘法");
        System.out.println("菜炒好了，也装好盘了，上桌");
    }

}
