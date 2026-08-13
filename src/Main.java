import com.kelokeloo.test.entity.*;

/**
 * 程序入口。
 *
 * <p>演示多态：同一个 {@code sayHello} 方法传入不同的 {@link Person} 对象，
 * 运行时根据对象的真实类型走不同分支——超人调用 {@link SuperMan#sayHello()}，
 * 普通人调用 {@link Person#getName()}。</p>
 *
 * @author kelo
 * @version 1.0
 * @since 2026-08-13
 */
public class Main {

    /**
     * 程序入口方法。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Person p1 = new NormalPerson("小明");
        SuperMan sm1 = SuperMan.getInstance();

        p1.study();
        sm1.study();

        System.out.println(p1);
    }

    /**
     * 根据对象的实际类型打招呼。
     *
     * <p>如果传入的是 {@code SuperMan} 实例，就调用超人的打招呼方法；
     * 否则按普通人处理，直接输出姓名。</p>
     *
     * @param person 任意 {@code Person}（或其子类）实例
     * @see SuperMan#sayHello()
     * @see Person#getName()
     */
    private static void sayHelo(Person person){
        if(person instanceof SuperMan){
            ((SuperMan) person).sayHello();
        }else {
            System.out.println("你好，我是" + person.getName());
        }

    }
}
