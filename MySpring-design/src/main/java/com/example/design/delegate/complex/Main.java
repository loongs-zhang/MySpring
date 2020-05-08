package com.example.design.delegate.complex;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class Main {
    public static void main(String[] args) {
        BOSS boss = new BOSS();
        boss.command(Worker.WorkType.backend, new Leader());
        boss.command(Worker.WorkType.frontEnd, new Leader());
        boss.command(Worker.WorkType.unknown, new Leader());
    }
}
