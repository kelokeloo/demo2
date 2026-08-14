package common;

import java.util.Objects;

public abstract class Person implements Cloneable, Study{
    protected String name;
    protected int age;
    protected String gender;

    public abstract void sayHello();

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Person(String name) {
        this.name = name;
        this.age = 10;
        this.gender = "男";
    }

    public void study(){
        System.out.println("学习中...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Person 未实现 Cloneable", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        return age == other.age
                && Objects.equals(name, other.name)
                && Objects.equals(gender, other.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
