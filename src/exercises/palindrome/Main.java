package exercises.palindrome;

/**
 * 回文字符串判断：正读与反读相同（如 "level"、"noon"）。
 * 双指针思路：i 从头走、n-1-i 从尾走，两两对比；
 * 一旦不等立即判定非回文，走到 i 与 n-1-i 相遇（奇长）或交错（偶长）则全部对称，是回文。
 * 中间字符无需与自己比较；空串、单字符天然为回文。
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("level : " + isPalindrome("level"));  // true  奇数长度
        System.out.println("noon  : " + isPalindrome("noon"));   // true  偶数长度
        System.out.println("abc   : " + isPalindrome("abc"));    // false 左右不对称
        System.out.println("a     : " + isPalindrome("a"));      // true  单字符
        System.out.println("空串  : " + isPalindrome(""));       // true  空串约定为回文
    }

    /**
     * 双指针判断 s 是否为回文串。
     *
     * @param s 任意字符串（可为空串）
     * @return true 表示回文
     */
    static boolean isPalindrome(String s) {
        int n = s.length();
        int i = 0;
        while (i < n - 1 - i) {                 // i >= n-1-i（相遇或交错）时停止
            if (s.charAt(i) != s.charAt(n - 1 - i)) {
                return false;                   // 左右不对称，直接判定非回文
            }
            i++;                                // 左指针右移，右指针 n-1-i 随之左移
        }
        return true;
    }
}
