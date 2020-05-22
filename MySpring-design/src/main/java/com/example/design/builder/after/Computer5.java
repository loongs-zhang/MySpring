package com.example.design.builder.after;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Computer5 {
    protected String cpu;
    protected String gpu;
    protected String ram;
    protected String screen;
    protected String keyboard;
    protected String speaker;
    protected List<String> usbs;
    protected String hardDisk;
    protected String hdmi;
    protected String bluetooth;
}
