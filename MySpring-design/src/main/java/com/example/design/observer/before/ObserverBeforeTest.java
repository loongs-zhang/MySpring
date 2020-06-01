package com.example.design.observer.before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public class ObserverBeforeTest {
    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        Student student1 = new Student("张三", teacher);
        Student student2 = new Student("李四", teacher);
        List<Student> students = new ArrayList<>(Arrays.asList(student1, student2));
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            teacher.catchStudent(students);
            teacher.continueTeach();
        }
    }
}
