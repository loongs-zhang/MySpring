package com.dragon.springframework.aop.support.expression.test;

import com.dragon.springframework.core.StringUtils;

/**
 * 任意输入两个字符串，求出这两个字符串的公共子序列
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
public class MaxSubStringTest {

    public static void main(String[] args) {
        String x = "com.dragon.springframework.test.service.impl.TestServiceImpl";
        String y = "* com.dragon.springframework.test.service.TestService.setType(java.lang.String,com.dragon.springframework.test.pojo.Type)";
        System.out.println(StringUtils.getMaxSubString(x, y));
    }

}