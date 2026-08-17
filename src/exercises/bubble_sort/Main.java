package exercises.bubble_sort;

import java.util.Arrays;

/**
 * 冒泡排序练习：方法接收 int[]，返回排序后的新数组（不修改原数组）。
 * 参考 topics 下各知识点的包结构：练习统一放 src/exercises/<exercise>/。
 */
public class Main {

    public static void main(String[] args) {
        int[] original = {5, 3, 8, 1, 2, 9, 4};
        int[] sorted = bubbleSort(original);

        System.out.println("原数组：" + Arrays.toString(original));
        System.out.println("排序后：" + Arrays.toString(sorted));
    }

    /**
     * 冒泡排序：相邻两两比较，大的往后"冒"。
     * 为避免副作用，先复制一份再排，调用方的原数组不受影响。
     *
     * @param source 原数组
     * @return 排序后的新数组
     */
    static int[] bubbleSort(int[] source) {
        int[] arr = Arrays.copyOf(source, source.length);
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;                 // 本轮是否发生过交换
            for (int j = 0; j < n - 1 - i; j++) {    // 每轮把最大的沉到末尾，内层可少比一轮
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;                     // 一轮没交换说明已经有序，提前结束
        }
        return arr;
    }
}
