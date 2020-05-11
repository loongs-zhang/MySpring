package com.example.design.singleton.before;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
public class NonSingleton {

    public NonSingleton() {
    }

    public void hello() {
    }

    public static void main(String[] args) {
        NonSingleton prototype1 = new NonSingleton();
        prototype1.hello();
        NonSingleton prototype2 = new NonSingleton();
        prototype2.hello();
    }
}
