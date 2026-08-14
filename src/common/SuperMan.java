package common;

/**
 * 超人实体类，继承自 {@link Person}。
 *
 * <p>它是对 {@code Person} 的扩展，额外拥有自己的 {@code name} 字段（遮蔽了父类字段）。
 * 同时提供 {@link #getInstance()} 快捷方法，返回一个共享的超人实例。</p>
 *
 * <p>示例：
 * {@code SuperMan superman = SuperMan.getInstance();
 * superman.sayHello();}</p>
 *
 * @author kelo
 * @version 1.0
 * @since 2026-08-13
 */
public class SuperMan extends Person {
    /** 超人自己的姓名（遮蔽父类 Person 的同名字段）。 */
    protected String name;
    /** 实例数量上限，目前固定为 1。 */
    private static final int count = 1;

    /**
     * 构造一个超人。
     *
     * <p>初始化时调用父类构造方法，将姓名设为"超人"、年龄 100、性别"男"。</p>
     */
    public SuperMan() {
        super("超人", 100, "男");
    }

    /** 共享的超人实例，类加载时创建。 */
    private static SuperMan p = new SuperMan();

    /**
     * 获取共享的超人实例。
     *
     * @return 共享的 {@code SuperMan} 实例（也可通过 {@code new SuperMan()} 自行创建）
     * @see SuperMan#SuperMan()
     */
    public static SuperMan getInstance() {
        return p;
    }

    /**
     * 打印超人打招呼的信息。
     *
     * <p>控制台输出当前实例的姓名，例如：{@code 我是超人}。</p>
     *
     * @see #getName()
     */
    public void sayHello() {
        System.out.println("我是" + name);
    }

    @Override
    public void study() {
        System.out.println("超人：");
        super.study();
    }
}
