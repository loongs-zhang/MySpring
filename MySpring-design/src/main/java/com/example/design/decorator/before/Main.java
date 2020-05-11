package com.example.design.decorator.before;

import com.example.design.decorator.after.EyeShadow;
import com.example.design.decorator.after.Lipstick;

/**
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class Main {
    public static void main(String[] args) {
        AbstractWoman woman = new OrdinaryWoman();
        woman = new MakeupArtist(woman, new Lipstick());
        woman = new MakeupArtist(woman, new EyeShadow());
        System.out.println(woman.getFaceValue());
    }
}
