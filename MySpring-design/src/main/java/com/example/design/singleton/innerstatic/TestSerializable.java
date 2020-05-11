package com.example.design.singleton.innerstatic;

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
            os.writeObject(InnerStaticLazySingleton.getInstance());
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            InnerStaticLazySingleton singleton = (InnerStaticLazySingleton) ois.readObject();
            System.out.println(InnerStaticLazySingleton.getInstance());
            System.out.println(singleton);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
