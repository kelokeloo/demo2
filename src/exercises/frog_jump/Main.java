package exercises.frog_jump;

/**
 * 青蛙跳台阶：n 个台阶，每次跳 1 或 2 阶，求到达顶端的方案数。
 * 原理：对"最后一步"分类——最后跳 1 阶（来自 n-1）或跳 2 阶（来自 n-2），
 * 两类互斥且完备，故 jump(n) = jump(n-1) + jump(n-2)。
 * 本类给出两种实现：递归 jump() 与迭代 jumpIterative()，结果一致。
 * 前提：n >= 1。
 */
public class Main {

    public static void main(String[] args) {
        // 递归与迭代结果应完全一致，这里对比验证
        System.out.println("jump(5)  递归：" + jump(5) + "  迭代：" + jumpIterative(5));     // 8
        System.out.println("jump(10) 递归：" + jump(10) + "  迭代：" + jumpIterative(10));   // 89
    }

    /**
     * 递归求跳 n 个台阶的方案数。
     *
     * @param n 台阶数，要求 n >= 1
     * @return 方案数
     */
    static int jump(int n) {
        if (n == 1) {
            return 1;                // 出口 1：只剩 1 阶，只能跳 1 阶
        }
        if (n == 2) {
            return 2;                // 出口 2：可跳 1+1，或直接跳 2
        }
        return jump(n - 1) + jump(n - 2);   // 最后一步分类：来自 n-1（跳 1 阶）或 n-2（跳 2 阶）
    }

    /**
     * 迭代求跳 n 个台阶的方案数：只需记住前两步结果，循环滚动累加，O(n) 时间 / O(1) 空间。
     *
     * @param n 台阶数，要求 n >= 1
     * @return 方案数
     */
    static int jumpIterative(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int prevTwo = 1;   // jump(1)
        int prevOne = 2;   // jump(2)
        for (int i = 3; i <= n; i++) {
            int cur = prevOne + prevTwo;   // jump(i) = jump(i-1) + jump(i-2)
            prevTwo = prevOne;             // 滚动：旧的"前 1 步"成为新的"前 2 步"
            prevOne = cur;                 // 本次结果成为新的"前 1 步"
        }
        return prevOne;
    }
}
