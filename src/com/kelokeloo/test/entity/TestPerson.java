package com.kelokeloo.test.entity;

/**
 * 测试用实体类，继承自 {@link SuperMan}。
 *
 * <p>它演示了多层继承下的字段遮蔽：本类又声明了一个 {@code name} 字段（初始值 "test"），
 * 于是 TestPerson 实例上其实存在三层同名 {@code name}，分别属于
 * {@code TestPerson}、{@code SuperMan}、{@code Person}。</p>
 *
 * @author kelo
 * @version 1.0
 * @since 2026-08-13
 * @see SuperMan#SuperMan()
 */
public class TestPerson extends SuperMan{
    /** 测试用姓名，遮蔽了父类 {@link SuperMan} 的 {@code name}。 */
    protected String name = "test";

    /**
     * 构造一个测试人员。
     *
     * <p>调用父类 {@link SuperMan} 的无参构造方法，父类再调用 {@link Person}
     * 的三参构造方法，把"超人、100、男"存进更上层的字段。</p>
     *
     * @see SuperMan#SuperMan()
     */
    public TestPerson(){
        super();
    }

    /**
     * 打印本对象各层 {@code name} 字段的值。
     *
     * <p>用于观察字段遮蔽的效果：第一行打印本类（TestPerson）的 name，
     * 第二行通过 {@code super.name} 打印直接父类（SuperMan）的 name。</p>
     *
     * @see #name
     */
    public void printName(){
        System.out.println(name);
        System.out.println(super.name);
    }

    public  void sayHello(){

    }
}
