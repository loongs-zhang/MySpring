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
public class Clone implements Cloneable {

    private String name;

    @Override
    protected Clone clone() {
        Clone clone = new Clone();
        clone.setName(this.name);
        return clone;
    }

    public static void main(String[] args) {
        Clone prototype = new Clone("007");
        Clone clone = prototype.clone();
        clone.setName("008");
        System.out.println(prototype);
        System.out.println(clone);
    }
}
