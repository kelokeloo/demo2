package topics.functional_interface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式接口：只有一个抽象方法的接口（@FunctionalInterface 校验），
 * 可以用 lambda / 方法引用直接实现，是 Stream 与回调的地基。
 *
 * 最常用的四个：
 *   Supplier<T>    无参有返回值  T get()                —— 生产/工厂
 *   Consumer<T>    有参无返回值  void accept(T)         —— 消费/副作用
 *   Function<T,R>  T 进 R 出     R apply(T)             —— 转换
 *   BiFunction<T,U,R>  两参一返回 R apply(T, U)         —— 双参转换
 *   Predicate<T>   有参返 boolean  boolean test(T)      —— 判断/筛选
 *
 * 特点：自带默认方法做组合（andThen 串联、compose 反向）；
 * 有原始类型/双参变体；可直接接方法引用（见 topics.method_reference）。
 */
public class Main {

    public static void main(String[] args) {
        // ===== 1. Supplier —— 只出不进：生产数据 =====
        Supplier<Double> random = () -> Math.random();
        System.out.println(random.get());                     // 0~1 随机数

        Supplier<List<String>> emptyList = ArrayList::new;    // () -> new ArrayList<>()
        System.out.println(emptyList.get().size());           // 0

        // ===== 2. Consumer —— 只进不出：消费数据、产生副作用 =====
        Consumer<String> printer = s -> System.out.println(s);
        printer.accept("hello");                              // hello

        List<String> log = new ArrayList<>();
        Consumer<String> collector = log::add;                // 方法引用接现成方法
        collector.accept("step1");
        System.out.println(log);                              // [step1]

        // ===== 3. Function —— 一进一出：类型转换 =====
        Function<String, Integer> len = String::length;       // s -> s.length()
        System.out.println(len.apply("abc"));                 // 3

        // andThen：先取长度再乘 2，组合成新 Function
        Function<String, Integer> twice = len.andThen(n -> n * 2);
        System.out.println(twice.apply("abc"));               // 6

        // compose：反向组合，先执行括号内的 times10，再执行 plus1
        Function<Integer, Integer> plus1 = n -> n + 1;
        Function<Integer, Integer> times10 = n -> n * 10;
        System.out.println(plus1.compose(times10).apply(3));  // 31

        // ===== 4. BiFunction —— 两参一返回 =====
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(3, 4));                  // 7

        // BiFunction 同样支持 andThen
        BiFunction<Integer, Integer, Integer> addThenDouble = add.andThen(n -> n * 2);
        System.out.println(addThenDouble.apply(3, 4));        // 14

        // ===== 5. Predicate —— 有参返 boolean：判断/筛选 =====
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println(isEven.test(4));                   // true
        System.out.println(isEven.test(5));                   // false

        // 默认方法组合：and（且）/ or（或）/ negate（取反）
        Predicate<Integer> positive = n -> n > 0;
        System.out.println(isEven.and(positive).test(4));     // true
        System.out.println(isEven.negate().test(5));          // true

        // 经典场景：Stream.filter —— 把 Predicate 传进去筛选
        System.out.println(List.of(1, 2, 3, 4, 5, 6)
                .stream().filter(isEven).toList());           // [2, 4, 6]
    }
}
