# 代码风格规范（精简优先）

> 适用于本 Java 学习项目。核心原则：**核心代码是主体，说明进注释，System.out 只留必要结果**。

## 总则

1. **`main()` 只放必要的演示调用**；解释性文字（"这是在演示什么"）放注释/类 Javadoc，不进 `System.out`。
2. **`System.out` 只打印结果本身**，期望值用行尾注释标注，不用字符串拼接加标签。
3. **简单、方便阅读优先**：能一行写完的不拆两行，能不加的修饰一律不加。

## System.out 使用约定

| 做法 | 示例 | 评价 |
|---|---|---|
| 只打印结果 + 行尾注释期望值 | `System.out.println(len.apply("abc")); // 3` | ✅ 推荐 |
| 字符串拼接标签前缀 | `System.out.println("len = " + len.apply("abc"));` | ❌ 冗余噪音 |
| 打印"我在演示什么" | `System.out.println("下面演示 Function 转换");` | ❌ 说明应进注释 |
| 分隔线刷屏 | `System.out.println("===== 第 1 节 =====");` | ❌ 干扰阅读 |

```java
// ✅ 推荐：结果 + 注释
Function<String, Integer> len = String::length;          // s -> s.length()
System.out.println(len.apply("abc"));                    // 3

// ❌ 不推荐：标签前缀 + 长拼接，两行都在讲同一件事
Function<String, Integer> len = String::length;
System.out.println("len(\"abc\") 的结果是 = " + len.apply("abc"));
```

## 注释约定

- **类 Javadoc**：讲清楚这个知识点解决什么问题、有哪些核心概念。
- **行内注释**：标注期望输出、等价写法（如 `// s -> s.length()`、`// 0`）。
- **辅助类归属**：见 [`code-organization.md`](code-organization.md) 的"类归属判断"。

## 反例对照（来自本主题历史版本）

原版本每个结果都用标签拼接，代码量翻倍、核心逻辑被淹没：

```java
// ❌ 旧版：9 处 System.out 全是 "xxx = " + 拼接，说明还重复了变量名
System.out.println("len(\"abc\") = " + len.apply("abc"));
System.out.println("twice(\"abc\") = " + twice.apply("abc"));
System.out.println("add(3, 4) = " + add.apply(3, 4));

// ✅ 现版：裸打印结果，期望值进注释
System.out.println(len.apply("abc"));                    // 3
System.out.println(twice.apply("abc"));                  // 6
System.out.println(add.apply(3, 4));                     // 7
```
