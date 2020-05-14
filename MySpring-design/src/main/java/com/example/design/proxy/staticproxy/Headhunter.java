package com.example.design.proxy.staticproxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Headhunter implements Worker {

    private Graduate graduate;

    @Override
    public void findJob() {
        if (graduate == null) {
            System.out.println("无人委托");
            return;
        }
        System.out.println("收集各个公司的人才需求");
        graduate.findJob();
        System.out.println("需求匹配，帮忙找到一份A公司的工作");
    }

    public static void main(String[] args) {
        Graduate graduate = new Graduate();
        Headhunter headhunter = new Headhunter(graduate);
        headhunter.findJob();
    }
}
