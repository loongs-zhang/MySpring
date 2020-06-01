package com.example.design.observer.after;

import com.example.design.observer.before.State;
import lombok.Getter;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public class Teacher2 {

    @Getter
    private volatile State state;

    public Teacher2() {
        this.state = State.TEACH;
    }

    public void catchStudent(List<Student2> students) {
        this.state = State.CATCH;
        for (Student2 student : students) {
            if (student.getState() == State.LISTEN) {
                System.out.println(student.getName() + " 在听课");
            } else {
                System.out.println(student.getName() + " 被抓了个现行");
            }
        }
    }

    public void continueTeach() {
        this.state = State.TEACH;
    }
}
