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
public class Clone3 implements Cloneable {

    private String name;

    @Override
    protected Clone3 clone() throws CloneNotSupportedException {
        return (Clone3) super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Clone3 prototype = new Clone3("007");
        Clone3 clone = prototype.clone();
        clone.setName("008");
        System.out.println(prototype);
        System.out.println(clone);
    }
}
