package topics.object_methods;

import common.NormalPerson;
import common.Person;

public class Main {

    public static void main(String[] args) {
        System.out.println("===== 1. toString：什么时候被自动调用 =====");
        Person p1 = new NormalPerson("小明");
        System.out.println(p1);
        String s = "我是" + p1;
        System.out.println(s);
        System.out.println("手动调用: " + p1.toString());

        System.out.println();
        System.out.println("===== 2. equals + hashCode：集合去重/查找场景 =====");
        Person a = new NormalPerson("小明", 10, "男");
        Person b = new NormalPerson("小明", 10, "男");
        System.out.println("a.equals(b) = " + a.equals(b));
        System.out.println("a.hashCode() = " + a.hashCode());
        System.out.println("b.hashCode() = " + b.hashCode());
        System.out.println("a 和 b 内容相同，equals 和 hashCode 都相等 => HashSet 会把它们当成同一个");

        System.out.println();
        System.out.println("===== 3. clone：想复制一份独立对象时 =====");
        Person copy = p1.clone();
        System.out.println("copy.getName() = " + copy.getName() + "（和原对象内容相同）");
        System.out.println("copy == p1 => " + (copy == p1) + "（但它们是两个独立对象）");
        copy.setName("小红");
        System.out.println("改副本后 p1.getName() = " + p1.getName() + "，原对象不受影响");

        System.out.println();
        System.out.println("===== 4. getClass / hashCode 的其他场景 =====");
        System.out.println("p1.getClass() = " + p1.getClass().getSimpleName());
        System.out.println("两个 new Person(\"小明\") 是同一对象吗？a == b => " + (a == b));
    }
}
