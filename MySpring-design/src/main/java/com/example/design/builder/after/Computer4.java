package com.example.design.builder.after;

import lombok.Builder;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@ToString
@Builder
@SuppressWarnings("unused")
public class Computer4 {
    private String cpu;
    private String gpu;
    private String ram;
    private String screen;
    private String keyboard;
    private String speaker;
    private List<String> usbs;
    private String hardDisk;
    private String hdmi;
    private String bluetooth;

    public static void main(String[] args) {
        Computer4 computer4 = Computer4.builder()
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
                .build();
        System.out.println(computer4);
    }
}
