package com.example.design.singleton.enumhungry;

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
            os.writeObject(EnumHungrySingleton.getInstance());
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            EnumHungrySingleton singleton = (EnumHungrySingleton) ois.readObject();
            System.out.println(EnumHungrySingleton.getInstance());
            System.out.println(singleton);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
