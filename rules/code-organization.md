# 代码组织规范

> 适用于本 Java 学习项目。每个知识点/练习对应一个包，用 `Main` 作为唯一演示入口。

## 总则

1. 项目按**知识点**组织代码，而不是按层（controller/service/entity）。
2. `topics/` 与 `exercises/` 结构相同：`src/topics/<topic>/` 演示语言特性（讲清楚），`src/exercises/<exercise>/` 动手练算法/解题（练编码）。
3. 每个知识点/练习是一个**包**，包路径 = 目录路径（`src` 为源码根目录）。
4. 每个包内有一个 `Main.java`（含 `public static void main`），作为该知识点的唯一入口。
5. 只放真正被多个主题复用的类到 `common/`，避免重复维护。

## 目录结构

```
demo2/src/
├── common/                     # 跨主题复用的基础类（如 Person 层级、接口）
├── topics/                     # 知识点包：演示语言特性
│   └── <topic>/                # 一个知识点
│       ├── Main.java           # 唯一演示入口
│       └── <知识点独有类>.java   # 只属于该知识点的类
└── exercises/                  # 练习包：动手练算法/解题
    └── <exercise>/             # 一个练习
        └── Main.java           # 唯一演示入口
```

## 规则

### 包名 / 目录命名
- 包名必须等于目录路径。例：`src/topics/polymorphism/Main.java` 的包名是 `topics.polymorphism`。
- 主题目录名必须是**合法 Java 标识符**：小写字母 + 数字 + 下划线，**禁用连字符 `-`**。
  - 正确：`final_keyword`、`object_methods`
  - 错误：`final-keyword`（不能作为包名）
- `Main` 类名在各包间可重复，靠包名区分，不会冲突。

### 新增知识点 / 练习流程
1. 知识点：在 `src/topics/` 下新建 `<topic>/`，包名 `topics.<topic>`；练习：在 `src/exercises/` 下新建 `<exercise>/`，包名 `exercises.<exercise>`。
2. 检查 `common/` 是否已有可复用类——**先复用，后新建**。
3. 编写 `Main.java` 作为演示入口；只属于本知识点的辅助类放同包下。

### 类归属判断
- **放 `common/`**：被 ≥2 个知识点使用的基础类（如 `Person`、`SuperMan`）。
- **放知识点包内**：只被该知识点使用（如 `TestPerson` 只在 `field_shadowing` 中演示）。
- **辅助类**：优先作为 `Main` 的内部静态类；当它足够大或可能被复用时才拆成独立文件。

### 运行方式
- IntelliJ：右键 `Main.java` → Run。
- 命令行：
  ```bash
  javac -d out $(find src -name '*.java')
  java -cp out topics.<topic>.Main    # 练习：java -cp out exercises.<exercise>.Main
  ```

## 现状（2026-08-14 迁移后）

| 知识点 | 包 | 入口 | 说明 |
|---|---|---|---|
| 多态 | `topics.polymorphism` | `Main.java` | 复用 `common` 的 Person 层级 |
| Object 方法 | `topics.object_methods` | `Main.java` | toString/equals/hashCode/clone |
| final 关键字 | `topics.final_keyword` | `Main.java` | Animal/Report 为内部类 |
| 字段遮蔽 | `topics.field_shadowing` | `Main.java` | 含 `TestPerson`（演示三层 name） |
| 静态内部类 | `topics.inner_class` | `Main.java` | 辅助类/单例 Holder，对比成员内部类 |
| 匿名内部类 | `topics.anonymous_inner_class` | `Main.java` | 回调 Button/Runnable/Comparator，对比 lambda |
| 泛型通配符 | `topics.generic_wildcards` | `Main.java` | 上界 ? extends / 下界 ? super / PECS |
| —（共享） | `common` | — | `Person` / `NormalPerson` / `SuperMan` / `Study` |

### 练习（exercises/）

| 练习 | 包 | 入口 | 说明 |
|---|---|---|---|
| 冒泡排序 | `exercises.bubble_sort` | `Main.java` | 复制后排序，不修改原数组 |
| 二分查找 | `exercises.binary_search` | `Main.java` | 有序数组查找，返回下标或 -1 |
| 青蛙跳台阶 | `exercises.frog_jump` | `Main.java` | 递归与迭代两种解法 |
| 回文判断 | `exercises.palindrome` | `Main.java` | 双指针左右对比 |
