package com.example.design.prototype.shallow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SuccessZhang
 * @date 2020/5/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clone2 implements Cloneable {

    private String name;

    @Override
    protected Clone2 clone() throws CloneNotSupportedException {
        return (Clone2) super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Clone2 prototype = new Clone2("007");
        Clone2 clone = prototype.clone();
        clone.setName("008");
        System.out.println(prototype);
        System.out.println(clone);
    }
}
