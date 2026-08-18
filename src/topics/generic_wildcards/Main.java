package topics.generic_wildcards;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型通配符边界：解决"泛型不变性"带来的方法复用难题。
 *
 * 核心问题：List<Integer> 不是 List<Number>，同一方法无法同时接受两者。
 * 解决方案：
 *   上界  <? extends T>  —— 接受 T 及其子类，只读（Producer Extends）
 *   下界  <? super T>    —— 接受 T 及其父类，只写（Consumer Super）
 *
 * 记忆口诀：PECS —— Producer Extends, Consumer Super
 *   从集合读数据（它产出数据）→ extends
 *   往集合写数据（它消费数据）→ super
 */
public class Main {

    public static void main(String[] args) {
        // ===== 1. 上界 ? extends —— 同时接受多种数字类型 =====
        System.out.println(sum(List.of(1, 2, 3)));          // 6.0
        System.out.println(sum(List.of(1.5, 2.5, 3.0)));   // 7.0
        System.out.println(sum(List.of(10L, 20L, 30L)));    // 60.0

        // ===== 2. 下界 ? super —— 同时写入多种父类容器 =====
        List<Number> numDest = new ArrayList<>();
        addIntegers(numDest);                   // Number 容器也能装 Integer
        System.out.println(numDest);            // [1, 2, 3]

        // ===== 3. PECS 综合：集合复制 =====
        // src 产出数据 → extends；dest 消费数据 → super
        List<Number> dest = new ArrayList<>();
        copy(dest, List.of(10, 20, 30));
        System.out.println(dest);               // [10, 20, 30]
    }

    // ----- 上界：从集合读数据，用 ? extends -----

    /**
     * 求任意 Number 子类集合的总和。
     * ? extends Number：list 可以是 List<Integer>、List<Double>、List<Long>……
     * 只读不写：编译器不允许往 list 里 add（不知道实际是哪种子类）。
     */
    static double sum(List<? extends Number> list) {
        double total = 0;
        for (Number n : list) total += n.doubleValue();
        // list.add(1);  // ❌ 不允许写入
        return total;
    }

    // ----- 下界：往集合写数据，用 ? super -----

    /**
     * 往任意能容纳 Integer 的集合里批量添加数据。
     * ? super Integer：list 可以是 List<Integer>、List<Number>、List<Object>。
     * 只写不读：读出来只能拿到 Object，类型信息丢失。
     */
    static void addIntegers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
        // Integer x = list.get(0);  // ❌ 编译报错，只能拿 Object
    }

    // ----- PECS 综合：src 生产（extends），dest 消费（super） -----

    /**
     * 把 src 中的元素逐个复制到 dest。
     * src  是数据来源（Producer）→ ? extends T
     * dest 是数据目标（Consumer）→ ? super T
     */
    static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (T item : src) dest.add(item);
    }
}
