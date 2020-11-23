package com.example.design.observer.after;

import com.example.design.observer.before.State;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public class Student2 extends Responder implements Notifier {

    @Getter
    private final String name;

    private List<Observer> observers;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Student2(Teacher2 teacher, String name) {
        super(teacher);
        this.observers = new ArrayList<>();
        this.state = State.PLAY;
        this.name = name;
        this.addObserver(this);
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.notify(super.teacher.getState());
            }
        });
    }

    @Override
    public boolean addObserver(Observer observer) {
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(Observer observer) {
        return observers.remove(observer);
    }

    @Override
    public void notify(State state) {
        for (Observer observer : observers) {
            switch (state) {
                case TEACH:
                    //老师讲课，咱就玩
                    observer.reply(State.PLAY);
                    break;
                case CATCH:
                    //老师来抓，咱就听
                    observer.reply(State.LISTEN);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
