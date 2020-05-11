package com.example.design.singleton.threadlocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
public class TestSerializable {
    public static void main(String[] args) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(ThreadLocalSingleton.getInstance());
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            ThreadLocalSingleton singleton = (ThreadLocalSingleton) ois.readObject();
            System.out.println(ThreadLocalSingleton.getInstance());
            System.out.println(singleton);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
