package com.example.design.observer.before;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public class Student {

    @Getter
    private final String name;

    @Getter
    private State state;

    private final Teacher teacher;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Student(String name, Teacher teacher) {
        this.name = name;
        this.state = State.PLAY;
        this.teacher = teacher;
        this.replyCatch();
    }

    public void replyCatch() {
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (teacher.getState() == State.CATCH) {
                    this.state = State.LISTEN;
                } else if (teacher.getState() == State.TEACH) {
                    this.state = State.PLAY;
                }
            }
        });
    }
}
