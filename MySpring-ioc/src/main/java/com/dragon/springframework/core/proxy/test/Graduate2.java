package com.dragon.springframework.core.proxy.test;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class Graduate2 implements Worker {
    @Override
    public void findJob() {
        System.out.println("我Java基础特扎实，期望能找到一份高薪的工作");
    }
}
