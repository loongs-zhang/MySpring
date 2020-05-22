package com.example.design.builder.after;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/22
 */
@Data
@SuppressWarnings("unused")
public class Computer3 {
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

    private Computer3() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Computer3 computer2 = new Computer3();

        public Builder cpu(String cpu) {
            computer2.setCpu(cpu);
            return this;
        }

        public Builder gpu(String gpu) {
            computer2.setGpu(gpu);
            return this;
        }

        public Builder ram(String ram) {
            computer2.setRam(ram);
            return this;
        }

        public Builder screen(String screen) {
            computer2.setScreen(screen);
            return this;
        }

        public Builder keyboard(String keyboard) {
            computer2.setKeyboard(keyboard);
            return this;
        }

        public Builder speaker(String speaker) {
            computer2.setSpeaker(speaker);
            return this;
        }

        public Builder usbs(List<String> usbs) {
            computer2.setUsbs(usbs);
            return this;
        }

        public Builder hardDisk(String hardDisk) {
            computer2.setHardDisk(hardDisk);
            return this;
        }

        public Builder hdmi(String hdmi) {
            computer2.setHdmi(hdmi);
            return this;
        }

        public Builder bluetooth(String bluetooth) {
            computer2.setBluetooth(bluetooth);
            return this;
        }

        public Computer3 build() {
            return this.computer2;
        }
    }

    public static void main(String[] args) {
        Computer3 computer3 = Computer3.builder()
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
        System.out.println(computer3);
    }
}
