package topics.field_shadowing;

import common.SuperMan;

import java.lang.reflect.Field;

/**
 * 字段遮蔽（Field Shadowing）演示入口。
 *
 * <p>Person -&gt; SuperMan -&gt; TestPerson 三层各自声明了同名 {@code name} 字段。
 * 字段的绑定发生在编译期、按"写代码时所在的类"解析，因此子类的同名字段会把
 * 父类字段"遮蔽"掉——这和方法的重写（运行时按真实类型动态派发）完全不同。</p>
 *
 * @author kelo
 * @since 2026-08-14
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("===== 1. TestPerson.printName()：本类字段 vs super 的字段 =====");
        TestPerson t = new TestPerson();
        t.printName();

        System.out.println();
        System.out.println("===== 2. 反射打印整条继承链上每一层的 name =====");
        dumpNames(t);
        System.out.println();
        dumpNames(new SuperMan());

        System.out.println();
        System.out.println("===== 3. 关键结论 =====");
        System.out.println("SuperMan 构造时 super(\"超人\",...) 赋值的是 Person.name，");
        System.out.println("SuperMan 自己声明的 name 字段从未被赋值，所以是 null。");
        System.out.println("printName() 里的 super.name 只跳一层（到 SuperMan），并不是最顶层的 Person.name。");
    }

    /**
     * 从类的最底层开始，逐层打印每一层声明的 {@code name} 字段的值。
     */
    private static void dumpNames(Object obj) throws ReflectiveOperationException {
        for (Class<?> c = obj.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
            Field f = c.getDeclaredField("name");
            f.setAccessible(true);
            System.out.println("  " + c.getSimpleName() + ".name = " + f.get(obj));
        }
    }
}
