package exercises.binary_search;

/**
 * 二分查找练习：接收已排序的 int[] 和要查找的值，
 * 返回值的下标；找不到返回 -1。
 * 前提：数组必须已升序排序（否则结果无意义）。
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11, 13};
        System.out.println("找 7：" + binarySearch(arr, 7));      // 3
        System.out.println("找 1：" + binarySearch(arr, 1));      // 0（左边界）
        System.out.println("找 13：" + binarySearch(arr, 13));    // 6（右边界）
        System.out.println("找 6：" + binarySearch(arr, 6));      // -1（不存在）
        System.out.println("找 0：" + binarySearch(arr, 0));      // -1（小于最小）
        System.out.println("找 15：" + binarySearch(arr, 15));    // -1（大于最大）
    }

    /**
     * 二分查找：每次取中间值，与目标比大小，缩小区间到一半。
     *
     * @param arr 已升序排序的数组
     * @param target 要查找的值
     * @return 目标下标；找不到返回 -1
     */
    static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {                       // <= 关键：low==high 时还要判断最后一个元素
            int mid = low + (high - low) / 2;       // 防溢出写法，等价于 (low+high)/2
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;                      // 目标在右半边
            } else {
                high = mid - 1;                     // 目标在左半边
            }
        }
        return -1;
    }
}
