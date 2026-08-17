# Java 方法引用为什么难用（但函数式并不难）

## 痛点

Java 方法不是「值」，方法必须挂在类/对象上，引用它需要 `::` 操作符，且只能在「目标类型是函数式接口」时使用：

```java
// ❌ 编译错误：String::toUpperCase 没有自己的类型，上下文没法推断，不能独立存
var f = String::toUpperCase;

// ✅ 能用了：赋值给函数式接口类型（只含一个抽象方法的接口），编译器才把它翻译成该接口实例
Function<String, String> f = String::toUpperCase;

List<String> upper = names.stream()
    .map(String::toUpperCase)   // map(...) 期望 Function<String, String>，目标类型在「等它」
    .toList();
```

难用之处：

1. **不能独立存在** —— `String::toUpperCase` 不是可随便传递的值，必须有函数式接口在等它。
2. **重载即歧义** —— `Integer::valueOf` 同时匹配多个重载，直接编译错误。
3. **受检异常不兼容** —— 函数式接口的 `apply` 不抛受检异常，调用 `Files::readString` 这类要手动包 try-catch。
4. **没有部分应用** —— 想绑住一个参数（`x -> x + 1`）还得写 lambda，方法引用办不到。
5. **`::` 与 `->` 两套语法** —— 同是「把行为当值传」，写法不统一。

> 注意：上面难用的都是 `::` 方法引用这个「糖」。Java 函数式的主力是 **函数式接口 + lambda**，这一路其实挺好用，见下节。

## 但函数式接口 + lambda 挺好用

自己写一个「接收行为」的 API——传函数进来，由对象在合适时机调用：

```java
class Formatter {
    private final String str;                       // 对象持有状态（OO）
    Formatter(String str) { this.str = str; }

    String apply(Function<String, String> cb) {     // 参数是「一段行为」（FP 的味道）
        return cb.apply(this.str);                  // 合适时机回调，行为从外部注入
    }
}

Formatter f = new Formatter("abc");
String out = f.apply(s -> "[" + s + "]");           // 调用方只给「怎么转换」，不管何时调用
// out = "[abc]"
```

- **`Function<T, R>` 就是函数类型的替身**：一个 `(T) -> R` 的函数。配套 `Predicate<T>`（`(T) -> boolean`）、`Consumer<T>`（`(T) -> void`）、`Supplier<T>`（`() -> T`），一套够用。
- **Stream 是最大受益者**：

```java
List<String> names = List.of("banana", "apple", "cherry");
List<Integer> lens = names.stream()
    .map(String::length)         // 每个元素 -> 长度（= s -> s.length()，只是简写）
    .filter(n -> n > 4)          // 只留长度 >4 的
    .toList();                   // [6, 5, 6]
```

方法引用在这里只是 lambda 的糖：`String::length` 与 `s -> s.length()` 等价，不用它照样写函数式。**难用的是 `::`，好用的是函数式本身。**

> 上面 `Formatter` 就是「OO 与 FP 结合」的日常形态：对象提供结构（持有 `str`），函数提供行为（`apply` 收进来的转换）。

## 根因

### 函数本身就是值

Python / Go / JavaScript 里函数就是普通值，引用一个方法不需要任何新语法：

```python
upper = list(map(str.upper, names))   # str.upper 就是函数本身
```

```go
sort.Slice(names, func(i, j int) bool { ... })  // 函数名直接可传
```

这些语言**不需要「方法引用」**，因为方法引用就是方法本身。

### 面向对象 vs 函数式

- **面向对象**：状态和行为绑成对象，你对着对象喊「做点什么」，它用自己的状态完成。
- **函数式**：行为本身就是数据，独立存在、随意传递（`map` 接收「怎么转换」，`filter` 接收「怎么判断」）。

Java 生于 1995（纯面向对象），函数式是 2014（Java 8）才硬补进来的。语言底层全是对象和原始类型，没有「函数值」这个位置。为了支持 lambda，只能规定 lambda 必须伪装成某个只有单一抽象方法的接口（于是背 SAM 接口家族），方法引用则发明 `::` 去搬运方法。

**这不是 Java 不会设计，而是它用「对象思维」去模拟「函数思维」，每一处嫁接都留下伤疤。**

## Kotlin 怎么处理

Kotlin 的函数类型是一等类型，和 `Int`、`String` 平起平坐：

```kotlin
val add: (Int, Int) -> Int = { a, b -> a + b }  // 类型就是 (Int, Int) -> Int
val f: (String) -> String = String::uppercase    // 引用可独立存在、当普通值用
```

- 不需要函数式接口家族 —— 一个 `(A, B) -> C` 语法全覆盖。
- 单参数自带 `it`：`list.map { it.uppercase() }`，比 Java 短。
- 没有受检异常，从根上解决第 3 点。
- 杀手锏「带接收者的函数类型」：`String.(Int) -> String`，行为绑定到接收者上传递，是协程/DSL 的地基。

`String::uppercase` 里的 `::` 是跟 Java 学的残留，但**不用它也行**——大部分时候 `{ it.uppercase() }` 更短。`::` 只是多一个选择，不是必需品。

## 一句话

Java 的方法不是值，方法引用只能靠 `::` 这层糖，难用；但函数式的主体是「函数式接口 + lambda」，这一路 Java 走得通、也好用。Kotlin 只是把函数类型做成一等类型，连 `::` 这层糖都不再是必需品。
