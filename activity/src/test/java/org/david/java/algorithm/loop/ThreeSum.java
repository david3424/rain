package org.david.java.algorithm.loop;

/**
 * Created by mac on 16/5/25.
 * 测试时间复杂度*
 */
public class ThreeSum {


    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
                        System.out.println("[key=" + i + ",value=" + a[i] + "]");
                        System.out.println("[key=" + j + ",value=" + a[j] + "]");
                        System.out.println("[key=" + k + ",value=" + a[k] + "]");
                        System.out.println("**************");
                    }
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 2, -3, 4, -1, -4};
        System.out.println(count(a));
    }
}
