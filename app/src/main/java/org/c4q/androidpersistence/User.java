package org.c4q.androidpersistence;

import java.io.Serializable;

/**
 * Created by keshwans on 6/5/15.
 */
public class User implements Serializable {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        User user = new User("Malala", 17);
        String stringRep = user.toString();
        String str2 = String.valueOf(user);
        System.out.println(user);
    }
}
