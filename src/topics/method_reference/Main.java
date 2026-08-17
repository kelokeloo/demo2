package topics.method_reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 方法引用：已有方法的「搬运工」——把现成方法直接接到函数式接口上。
 * 比 lambda 更短，且自带方法名语义。
 *
 * 四种形态：
 *   1. 静态方法引用   ClassName::staticMethod      （Main::greet、Integer::parseInt）
 *   2. 特定对象引用   instance::method             （System.out::println）
 *   3. 任意对象引用   ClassName::instanceMethod    （String::toUpperCase、User::getName）
 *   4. 构造器引用     ClassName::new               （ArrayList::new、User::new）
 *
 * 高频场景：Stream 管道、Comparator 排序、构造器工厂、回调。
 *
 * 经验法则：
 *   有现成方法且语义对上 → 方法引用（短、自带名字）
 *   逻辑需要现场写（表达式/绑参数/多行） → lambda
 */
public class Main {

    public static void main(String[] args) {
        // 数据准备：注意用可变 List（List.of 不可变，无法排序）
        List<User> users = new ArrayList<>(List.of(
                new User("banana", 8),
                new User("apple", 5),
                new User("cherry", 6)));

        // ===== 1. Stream 管道（任意对象实例方法引用） =====
        List<String> names = users.stream()
                .map(User::getName)            // u -> u.getName()       提取字段
                .map(String::toUpperCase)      // s -> s.toUpperCase()   现成转换
                .filter(s -> s.length() > 5)   // 现场判断逻辑 → lambda
                .toList();
        System.out.println("1) Stream 管道: " + names);   // [BANANA, CHERRY]

        // ===== 2. Comparator 排序（getter 天生适合方法引用） =====
        users.sort(Comparator.comparing(User::getAge)     // 按年龄
                .thenComparing(User::getName));           // 同年龄按名字
        System.out.println("2) 排序后: " + users);         // [apple(5), cherry(6), banana(8)]

        // ===== 3. 构造器引用（工厂：延迟的、可传递的 new） =====
        Supplier<List<String>> factory = ArrayList::new;  // () -> new ArrayList<>()
        List<String> a = factory.get();
        List<String> b = factory.get();
        System.out.println("3) 每次 get 都是新实例: " + (a != b));   // true

        // Stream 里指定收集到的集合类型
        List<String> collected = names.stream()
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("   收集到指定集合: " + collected);

        // ===== 4. 回调（特定对象实例方法引用） =====
        users.forEach(System.out::println);   // 把 System.out 的 println 注册进 forEach

        // 自定义回调：把静态方法 Main::greet 注册进 Printer，由对方在合适时机调用
        Printer printer = new Printer();
        printer.register(Main::greet);        // 等价 printer.register(s -> greet(s))
        printer.run("世界");                  // 你好，世界

        // ===== 5. 静态方法引用 =====
        Function<String, Integer> parse = Integer::parseInt;  // s -> Integer.parseInt(s)
        System.out.println("5) 静态方法引用: " + (parse.apply("42") + 1));   // 43
    }

    /** 静态方法，供静态方法引用 Main::greet */
    static String greet(String name) {
        return "你好，" + name;
    }

    /** 回调接口 + 持有方，演示「把方法注册进去，由对方在合适时机调用」 */
    interface Handler {
        String handle(String msg);
    }

    static class Printer {
        private Handler handler;

        void register(Handler handler) {
            this.handler = handler;
        }

        void run(String msg) {
            if (handler != null) {
                System.out.println(handler.handle(msg));
            }
        }
    }

    /** 辅助数据类 */
    static class User {
        private final String name;
        private final int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
}
