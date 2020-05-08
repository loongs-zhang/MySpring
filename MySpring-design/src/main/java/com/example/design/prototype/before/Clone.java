package com.example.design.prototype.before;

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
public class Clone {

    private String name;

    public static void main(String[] args) {
        Clone prototype = new Clone("007");
        Clone clone = new Clone("008");
        System.out.println(prototype);
        System.out.println(clone);
    }
}
