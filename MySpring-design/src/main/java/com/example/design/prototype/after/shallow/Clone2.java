package com.example.design.prototype.after.shallow;

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
    protected Clone2 clone() {
        Clone2 clone = new Clone2();
        clone.setName(this.name);
        return clone;
    }

    public static void main(String[] args) {
        Clone2 prototype = new Clone2("007");
        Clone2 clone = prototype.clone();
        clone.setName("008");
        System.out.println(prototype);
        System.out.println(clone);
    }
}
