package com.example.design.proxy.staticproxy;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class Graduate implements Worker {
    @Override
    public void findJob() {
        System.out.println("我Java基础特扎实，期望能找到一份高薪的工作");
    }
}
