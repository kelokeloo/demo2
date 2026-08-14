package topics.text_blocks;

/**
 * 文本块（Text Blocks）：Java 15（JEP 378）正式引入的多行字符串。
 *
 * 先纠正一个叫法：""" 不是运算符，而是字符串字面量的「定界符」（delimiter）——
 * 和普通字符串的 " 性质相同，只是内容可以跨行书写。
 *
 * 核心特性：
 * 1. 换行、缩进、双引号全部原样保留 → SQL / HTML / JSON 可直接粘贴，
 *    不用 + 拼接、不用 \n 手动换行、不用 \" 转义（对比：普通字符串这三样全要）；
 * 2. 编译器自动剥掉「公共前导空白」，子元素相对缩进保留；
 * 3. 每行行尾空白自动删除（用 \s 可保留一个空格）；
 * 4. 转义仍生效：\n、\t 照常；行尾一个 \ 表示「续行」，不产生换行。
 *
 * 易错点：
 * 1. 开头三个双引号后面必须紧跟换行；
 * 2. 内容里不能连续出现三个及以上双引号；
 * 3. 内容行不能以反斜杠开头（会被当作转义字符）。
 *
 * 阅读方式：每个方法把「文本块源码」写在开头，那本身就是知识点；
 * 输出只用于验证源码里「看不出来」的行为（缩进剥离、行尾空白、续行），
 * 其余读源码就够了，不必盯着运行结果。
 */
public class Main {

    public static void main(String[] args) {
        syntax();   // 多行、无拼接
        sql();      // 换行缩进原样保留
        html();     // 双引号不用转义
        indent();           // 公共缩进自动剥离
        closingDelimiter(); // 结尾 """ 的位置影响缩进剥离量
        trailing();         // 行尾空白自动删除、\s 保留
        wrap();             // 行尾 \ 续行
    }

    // 打印小助手：label + 值 + 空行打包，少写重复的 System.out
    static void show(String label, String value) {
        System.out.println(label);
        System.out.println(value);
        System.out.println();
    }

    // 1. 多行 + 无拼接：写几行就是几行
    static void syntax() {
        String poem = """
                床前明月光，
                疑是地上霜。
                """;
        show("语法：换行原样保留 →", poem);
    }

    // 2. SQL：数据库客户端里长什么样，这里就长什么样
    static void sql() {
        String query = """
                SELECT u.id, u.name, COUNT(o.id) AS cnt
                FROM user u
                LEFT JOIN `order` o ON o.user_id = u.id
                WHERE u.status = 'active'
                GROUP BY u.id, u.name
                ORDER BY cnt DESC;
                """;
        show("SQL：", query);
    }

    // 3. HTML：class="..."、href="..." 里的双引号一个都不用转义
    static void html() {
        String page = """
                <!DOCTYPE html>
                <html>
                  <body>
                    <div class="container">
                      <button id="submit" class="btn btn-primary">提交</button>
                      <a href="/index" target="_blank">返回首页</a>
                    </div>
                  </body>
                </html>
                """;
        show("HTML：", page);
    }

    // 4. 公共缩进自动剥离；子元素多缩进的部分保留
    //    用 [ ] 包住输出，能看清值和边界的精确关系
    static void indent() {
        String list = """
                <ul>
                  <li>苹果</li>
                  <li>香蕉</li>
                </ul>
                """;
        show("缩进：公共部分被剥掉，<li> 多缩进的 2 格保留 →", "[" + list + "]");
    }

    // 5. 结尾 """ 的位置会参与「公共缩进」的计算，影响被剥掉多少：
    //    - 结尾 """ 与内容行同缩进 → 公共缩进被剥干净（上面 demo 都是这种）
    //    - 结尾 """ 更靠左 → 最小缩进被压低，内容反而会保留更多空白
    //    这也是「文本块必须写最左边」那个说法的来源：
    //    当某一行真的顶到列 0 时，最小缩进变成 0，什么都剥不掉，
    //    于是整段缩进都进了字符串——被这类坑坑过的人，才会总结成「必须顶格写」。
    static void closingDelimiter() {
        String sameLevel = """
                <ul>
                  <li>苹果</li>
                </ul>
                """;   // 结尾 """ 与内容同缩进 → 公共缩进被剥掉
        show("结尾 \"\"\" 与内容行同缩进 →", "[" + sameLevel + "]");

        String leftMost = """
                <ul>
                  <li>苹果</li>
                </ul>
        """;           // 结尾 """ 顶到列 0 → 最小缩进变 0 → 全部缩进保留
        show("结尾 \"\"\" 顶到列 0（缩进全部保留）→", "[" + leftMost + "]");
    }

    // 6. 行尾空白会被自动删除；\s 能保住一个空格
    static void trailing() {
        String plain = """
                a
                b
                """;
        show("行尾空白自动删除 →", "[" + plain + "]");

        String kept = """
                a\s
                b\s
                """;
        show("\\s 保留行尾空格 →", "[" + kept + "]");
    }

    // 7. 行尾一个 \ 表示续行：下一行直接接上来，不产生换行
    static void wrap() {
        String sentence = """
                The quick \
                brown fox \
                jumps over \
                the lazy dog.
                """;
        show("行尾 \\ 续行（实际是同一个值）→", "[" + sentence + "]");
    }
}
