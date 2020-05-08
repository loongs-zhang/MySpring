package com.example.design.prototype.after.deep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/5/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Cloneable, Serializable {

    private String name;

    private int age;

    private List<String> hobbies;

    @Override
    protected Student clone() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Student) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(10);
        student1.setHobbies(new ArrayList<>());
        Student student2 = student1.clone();
        student2.setName("李四");
        student2.getHobbies().add("唱歌");
        System.out.println(student1);
        System.out.println(student2);
    }
}
