package com.example.design.builder.after;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@ToString(callSuper = true)
@SuperBuilder
public class Laptop2 extends Computer6 {

    private Double weight;

    public static void main(String[] args) {
        Laptop2 laptop = Laptop2.builder()
                .cpu("i7-8750h")
                .gpu("gtx1070")
                .ram("16g")
                .screen("120hz screen")
                .keyboard("msi keyboard")
                .speaker("sairui")
                .usbs(new ArrayList<>(Arrays.asList("usb-a", "usb-a", "usb-a", "usb-c")))
                .hardDisk("1t")
                .hdmi("hdmi")
                .bluetooth("bluetooth5")
                .weight(2.0)
                .build();
        System.out.println(laptop);
    }
}
