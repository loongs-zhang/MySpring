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
public class Computer2 {
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

    private Computer2() {
    }

    private abstract static class AbstractBuilder {

        protected Computer2 computer2 = new Computer2();

        public abstract AbstractBuilder cpu(String cpu);

        public abstract AbstractBuilder gpu(String gpu);

        public abstract AbstractBuilder ram(String ram);

        public abstract AbstractBuilder screen(String screen);

        public abstract AbstractBuilder keyboard(String keyboard);

        public abstract AbstractBuilder speaker(String speaker);

        public abstract AbstractBuilder usbs(List<String> usbs);

        public abstract AbstractBuilder hardDisk(String hardDisk);

        public abstract AbstractBuilder hdmi(String hdmi);

        public abstract AbstractBuilder bluetooth(String bluetooth);

        public Computer2 build() {
            return this.computer2;
        }
    }

    public static class Builder extends AbstractBuilder {

        @Override
        public AbstractBuilder cpu(String cpu) {
            computer2.setCpu(cpu);
            return this;
        }

        @Override
        public AbstractBuilder gpu(String gpu) {
            computer2.setGpu(gpu);
            return this;
        }

        @Override
        public AbstractBuilder ram(String ram) {
            computer2.setRam(ram);
            return this;
        }

        @Override
        public AbstractBuilder screen(String screen) {
            computer2.setScreen(screen);
            return this;
        }

        @Override
        public AbstractBuilder keyboard(String keyboard) {
            computer2.setKeyboard(keyboard);
            return this;
        }

        @Override
        public AbstractBuilder speaker(String speaker) {
            computer2.setSpeaker(speaker);
            return this;
        }

        @Override
        public AbstractBuilder usbs(List<String> usbs) {
            computer2.setUsbs(usbs);
            return this;
        }

        @Override
        public AbstractBuilder hardDisk(String hardDisk) {
            computer2.setHardDisk(hardDisk);
            return this;
        }

        @Override
        public AbstractBuilder hdmi(String hdmi) {
            computer2.setHdmi(hdmi);
            return this;
        }

        @Override
        public AbstractBuilder bluetooth(String bluetooth) {
            computer2.setBluetooth(bluetooth);
            return this;
        }
    }

    public static class Director {

        private final Builder builder;

        public Director(Builder builder) {
            this.builder = builder;
        }

        public Computer2 build() {
            return builder.cpu("i7-8750h")
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
        }
    }

    public static void main(String[] args) {
        Builder builder = new Builder();
        System.out.println(builder.build());
        Director director = new Director(builder);
        System.out.println(director.build());
    }
}
