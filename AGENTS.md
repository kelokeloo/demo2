# 项目约定（Agents）

本项目是一个 **Java 学习项目**，按知识点组织代码。本文件只做概述，详细规范见 `rules/` 目录。

## 核心约定速览

- **一个知识点 = 一个包 = `src/topics/<topic>/`**，包内 `Main.java` 是该知识点的唯一演示入口。
- **一个练习 = 一个包 = `src/exercises/<exercise>/`**：`topics/` 演示语言特性（讲清楚），`exercises/` 动手练算法/解题（练编码），两者都以包内 `Main.java` 为唯一入口。
- **跨主题复用的基础类**放 `src/common/`，只属于单个知识点的类放该知识点包内。
- **包名 = 目录路径**（`src` 为源码根目录）；主题名用合法 Java 标识符（小写 + 下划线，禁用连字符，如 `final_keyword`）。
- 新增知识点时先查 `common/` 是否已有可复用类，避免重复。

## 详细规范

- 代码组织：见 [`rules/code-organization.md`](rules/code-organization.md)。
- 精简风格：见 [`rules/code-style.md`](rules/code-style.md)。

> 本文件遵循"简要 + 索引"原则，不放长内容；更细的规则一律落到 `rules/` 下。
