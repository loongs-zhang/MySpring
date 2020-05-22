package com.example.design.builder.after;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@ToString
@SuperBuilder
public class Computer6 {
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
}
