package com.example.design.builder.after;

import lombok.ToString;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@ToString
//@Builder
public class Laptop extends Computer5 {

    protected Double weight;

    public static void main(String[] args) {
//        Laptop laptop = Laptop.builder()
//                .weight(2.0)
//                .cpu("i7-8750h")
//                .gpu("gtx1070")
//                .ram("16g")
//                .screen("120hz screen")
//                .keyboard("msi keyboard")
//                .speaker("sairui")
//                .usbs(new ArrayList<>(Arrays.asList("usb-a", "usb-a", "usb-a", "usb-c")))
//                .hardDisk("1t")
//                .hdmi("hdmi")
//                .bluetooth("bluetooth5")
//                .build();
//        System.out.println(laptop);
    }
}
