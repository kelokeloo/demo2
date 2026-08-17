package topics.inner_class;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态内部类（static nested class）：用 static 修饰、定义在类内部的类。
 *
 * 演示点：
 * 1. 基础形态：把强相关的辅助类放进外部类，直接「外部类.内部类」使用
 * 2. 与成员内部类的区别：静态内部类不持有外部实例引用
 * 3. 单例模式：用静态内部类 Holder 持有唯一实例（懒加载 + 线程安全）
 * 4. 对比饿汉式单例：类一加载就 new，可能白白创建
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("===== 1. 基础形态：强相关的辅助类定义为静态内部类 =====");
        // Config 是商品配置，ConfigItem 是它的一个配置项，二者强相关
        Config config = new Config("server");
        config.add(new Config.ConfigItem("host", "127.0.0.1"));
        config.add(new Config.ConfigItem("port", "8080"));
        config.print();

        System.out.println();
        System.out.println("===== 2. 区别：静态内部类 vs 成员内部类 =====");
        // 静态内部类：直接 new，不需要外部对象
        Counter.Cell cell = new Counter.Cell();
        cell.increment();
        System.out.println("Counter.Cell（静态）直接创建并自增，value = " + cell.value);

        // 成员内部类：必须先有外部实例，再用「外部实例.new 内部类()」创建
        Counter counter = new Counter();
        Counter.MemCell memCell = counter.new MemCell();
        memCell.addToOuter(5);
        System.out.println("Counter.MemCell（成员）能访问外部字段 total = " + counter.total);
        System.out.println("成员内部类隐式持有外部实例引用，可能内存泄漏；静态内部类没有这个引用");

        System.out.println();
        System.out.println("===== 3. 单例模式：静态内部类 Holder（懒加载 + 线程安全）=====");
        System.out.println("第一次调用 getInstance() 才创建实例：");
        Singleton s1 = Singleton.getInstance(); // 此行打印一次「Singleton 构造」
        System.out.println("第二次调用不再创建：");
        Singleton s2 = Singleton.getInstance(); // 不再打印构造
        System.out.println("s1 == s2 ？ " + (s1 == s2) + "（是，全局唯一）");
        System.out.println("懒加载原理：Holder 只在第一次被引用时才加载，实例随 Holder 只创建一次；");
        System.out.println("线程安全原理：JVM 保证类加载只发生一次且线程安全，无需加锁。");

        System.out.println();
        System.out.println("===== 4. 对比：饿汉式单例（类加载就创建）=====");
        EagerSingleton e1 = EagerSingleton.getInstance();
        EagerSingleton e2 = EagerSingleton.getInstance();
        System.out.println("e1 == e2 ？ " + (e1 == e2) + "（是，同样唯一）");
        System.out.println("区别：饿汉式在类一加载就 new 实例，即使没人调用 getInstance() 也白白创建");
    }

    /**
     * 示例：商品配置。ConfigItem 与 Config 强相关，做成静态内部类。
     */
    static class Config {
        private final String name;
        private final List<ConfigItem> items = new ArrayList<>();

        Config(String name) {
            this.name = name;
        }

        void add(ConfigItem item) {
            items.add(item);
        }

        void print() {
            System.out.println("配置 " + name + "：");
            for (ConfigItem item : items) {
                System.out.println("  " + item.key + " = " + item.value);
            }
        }

        // 静态内部类：ConfigItem 只属于 Config，不独立存在
        static class ConfigItem {
            private final String key;
            private final String value;

            ConfigItem(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }
    }

    /**
     * 示例：计数器。对比静态内部类 Cell 与成员内部类 MemCell。
     */
    static class Counter {
        int total; // 供成员内部类访问的外部字段

        // 静态内部类：不持有外部实例引用，无法访问 total
        static class Cell {
            int value;

            void increment() {
                value++;
            }
        }

        // 成员内部类：隐式持有 Counter.this，可直接访问外部字段
        class MemCell {
            void addToOuter(int n) {
                total += n;
            }
        }
    }

    /**
     * 单例（Holder 写法）：懒加载 + 线程安全，业内推荐。
     * 原理：Singleton 类加载时 Holder 不会初始化（还没用到它）；
     * 第一次调用 getInstance() 引用 Holder，JVM 才触发 Holder 类加载，
     * 类加载线程安全且只发生一次，所以实例唯一且不会被重复创建。
     */
    static class Singleton {
        // 静态内部类持有唯一实例
        private static class Holder {
            private static final Singleton INSTANCE = new Singleton();
        }

        private Singleton() {
            System.out.println("Singleton 构造：唯一实例创建");
        }

        static Singleton getInstance() {
            return Holder.INSTANCE;
        }
    }

    /**
     * 单例（饿汉式）：类一加载就 new 实例，简单但可能白白创建。
     */
    static class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {
            System.out.println("EagerSingleton 构造：类加载时就创建");
        }

        static EagerSingleton getInstance() {
            return INSTANCE;
        }
    }
}
