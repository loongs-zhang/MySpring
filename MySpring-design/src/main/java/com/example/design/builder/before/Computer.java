package com.example.design.builder.before;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Computer {
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
        List<String> usbs = Arrays.asList("usb-a", "usb-a", "usb-a", "usb-c");

        Computer computer1 = new Computer("i7-8750h", "gtx1070", "16g",
                "120hz screen", "msi keyboard", "sairui", new ArrayList<>(usbs),
                "1t", "hdmi", "bluetooth5");
        System.out.println(computer1);

        Computer computer2 = new Computer();
        computer2.setCpu("i7-8750h");
        computer2.setGpu("gtx1070");
        computer2.setRam("16g");
        computer2.setScreen("120hz screen");
        computer2.setKeyboard("msi keyboard");
        computer2.setSpeaker("sairui");
        computer2.setUsbs(new ArrayList<>(usbs));
        computer2.setHardDisk("1t");
        computer2.setHdmi("hdmi");
        computer2.setBluetooth("bluetooth5");
        System.out.println(computer2);
    }
}
