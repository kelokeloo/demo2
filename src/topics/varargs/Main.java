package topics.varargs;

import java.util.Arrays;
import java.util.List;

/**
 * 可变参数（varargs）：方法参数个数不固定，用「类型... 参数名」声明。
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("===== 1. 声明与调用：可以传 0 到多个值 =====");
        printAll("a", "b", "c");
        printAll("only one");
        printAll(); // 一个都不传也可以

        System.out.println();
        System.out.println("===== 2. 方法体内：可变参数本质就是一个数组 =====");
        sum(1, 2, 3, 4, 5);
        sum();

        System.out.println();
        System.out.println("===== 3. 直接传数组：与逐个传值等价 =====");
        String[] words = {"hello", "world"};
        printAll(words);
        printAll(new String[]{"直接", "new", "数组"});

        System.out.println();
        System.out.println("===== 4. 可变参数必须是最后一个参数 =====");
        log("INFO", "request", "user=1");
        log("WARN");

        System.out.println();
        System.out.println("===== 5. 重载时：固定参数版本优先 =====");
        hello();
        hello("world");

        System.out.println();
        System.out.println("===== 6. JDK 里的常见例子 =====");
        String msg = String.format("%s 今天消费了 %.1f 元", "小明", 88.5);
        System.out.println(msg);
        List<String> list = List.of("a", "b", "c");
        System.out.println(list);

        System.out.println();
        System.out.println("===== 7. 单向规则：数组声明不能被逐个传值调用 =====");
        // 数组形式声明的方法：只能传数组
        arrForm(new String[]{"a", "b"});
        System.out.println("arrForm 只能传数组，不能逐个传值，也不能传 0 个（见下方编译错误）");
        // 下面两行编译不过，取消注释可看报错现场：
        // arrForm("a", "b");                       // 找不到 arrForm(String, String)
        // arrForm();                               // 找不到 arrForm()
        System.out.println("——对比：varForm 声明成可变参数，两种写法都行——");
        varForm("a", "b");
        varForm(new String[]{"a", "b"});
        varForm();
    }

    // 第 7 节用：可变参数声明（有「打包」特权）
    static void varForm(String... items) {
        System.out.println("varForm 收到 " + items.length + " 个");
    }

    // 第 7 节用：数组声明（调用方必须自己包数组）
    static void arrForm(String[] items) {
        System.out.println("arrForm 收到 " + items.length + " 个");
    }

    // 1&3. 基本用法：String... 在方法体内就是一个 String[]
    static void printAll(String... items) {
        System.out.println("收到 " + items.length + " 个参数：" + Arrays.toString(items));
    }

    // 2. 数组的本质：可以直接 for-each / 下标遍历
    static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) {
            total += n;
        }
        System.out.println("sum = " + total);
        return total;
    }

    // 4. 前面可以有固定参数，可变参数只能放最后
    static void log(String level, String... tags) {
        System.out.println("[" + level + "] " + Arrays.toString(tags));
    }

    // 5. 重载：hello() 和 hello(String...)，不传参时固定版本更"精确"，被优先选中
    static void hello() {
        System.out.println("hello(): 固定参数版本被调用");
    }

    static void hello(String... names) {
        System.out.println("hello(String...): 可变参数版本被调用");
    }
}
