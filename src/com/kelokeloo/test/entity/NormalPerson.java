package com.kelokeloo.test.entity;

public class NormalPerson extends Person {

    public NormalPerson(String name) {
        super(name);
    }

    public NormalPerson(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void sayHello() {
        System.out.println("你好，我是" + name);
    }
}
