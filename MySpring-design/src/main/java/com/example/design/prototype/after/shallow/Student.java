package com.example.design.prototype.after.shallow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/5/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Cloneable {

    private String name;

    private int age;

    private List<String> hobbies;

    @Override
    protected Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }

    public static void main(String[] args) {
        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(10);
        student1.setHobbies(new ArrayList<>());
        try {
            Student student2 = student1.clone();
            student2.setName("李四");
            student2.getHobbies().add("打篮球");
            System.out.println(student1);
            System.out.println(student2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
