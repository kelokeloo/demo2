package exercises.hanoi_tower;

/**
 * 汉诺塔：把 n 个盘子从 A 移到 C，只能小盘压大盘、一次一个。
 * 思路（正是递推 f(n) = 2·f(n-1) + 1）：
 *   ① 先把 n-1 个盘子 A → B（腾出最底下的最大盘）
 *   ② 最大盘 A → C（1 次）
 *   ③ 再把 n-1 个盘子 B → C
 * 方法 move 递归打印每一步，并返回移动次数。
 * 前提：n >= 1。
 */
public class Main {

    public static void main(String[] args) {
        int n = 3;
        System.out.println("===== 汉诺塔 n=" + n + " 移动步骤 =====");
        int moves = move(n, 'A', 'B', 'C');
        System.out.println("共 " + moves + " 次（验证 2^" + n + " - 1 = " + ((1 << n) - 1) + "）");
    }

    /**
     * 把 n 个盘子从 from 移到 to，via 为中间杆。
     *
     * @return 移动次数
     */
    static int move(int n, char from, char via, char to) {
        if (n == 1) {
            System.out.println(from + " -> " + to);   // 出口：只剩一个盘，直接搬
            return 1;
        }
        int count = move(n - 1, from, to, via);       // ① n-1 个盘 from → via（to 当垫脚）
        System.out.println(from + " -> " + to);       // ② 最大盘 from → to
        count += move(n - 1, via, from, to);          // ③ n-1 个盘 via → to
        return count + 1;
    }
}
