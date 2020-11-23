package com.example.design.observer.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public class ObserverAfterTest {
    public static void main(String[] args) {
        Teacher2 teacher = new Teacher2();
        Student2 student1 = new Student2(teacher, "张三");
        Student2 student2 = new Student2(teacher, "李四");
        Student2 student3 = new Student2(teacher, "王五");

        //我为人人，人人为我
        student1.addObserver(student2);
        student1.addObserver(student3);
        student2.addObserver(student1);
        student2.addObserver(student3);
        student3.addObserver(student1);
        student3.addObserver(student2);

        List<Student2> students = new ArrayList<>(Arrays.asList(student1, student2, student3));
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
