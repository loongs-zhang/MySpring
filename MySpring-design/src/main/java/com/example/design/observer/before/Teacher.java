package com.example.design.observer.before;

import lombok.Getter;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public class Teacher {

    @Getter
    private volatile State state;

    public Teacher() {
        this.state = State.TEACH;
    }

    public void catchStudent(List<Student> students) {
        this.state = State.CATCH;
        for (Student student : students) {
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
