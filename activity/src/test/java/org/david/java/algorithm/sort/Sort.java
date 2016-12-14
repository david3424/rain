package org.david.java.algorithm.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by roverll on 5/29/16.
 */
public class Sort {
    public static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> void exch(T[] array, int i, int j) {
        T t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] array) {
        if (array.length <= 1)
            return true;
        for (int i = 1; i < array.length; i++) {
            if (less(array[i], array[i - 1]))
                return false;
        }
        return true;
    }

    public static <T> void show(T[] array) {
        for (T t : array) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public static <T> void show(List<T> list) {
        for (T t : list) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public static <T extends Comparable<T>> void select(T[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (less(array[j], array[minIndex])) {
                    minIndex = j;
                }
            }
            exch(array, i, minIndex);
        }
    }

    public static <T extends Comparable<T>> void popup(T[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (less(array[j + 1], array[j])) {
                    exch(array, j, j + 1);
                }
            }
        }
    }

    public static <T extends Comparable<T>> void insert(T[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(array[j], array[j - 1])) {
                    exch(array, j, j - 1);
                }
            }
        }
    }

    public static <T extends Comparable<T>> void insert2(T[] array) {
        /**
         * 这个比insert可能更直观,但是代码更复杂, 把比较和移位分开了,所以直观不等于程序友好
         */
        for (int i = 1; i < array.length; i++) {
            T t = array[i];
            int j = i;
            while (j > 0 && less(t, array[j - 1]))
                j--;
            System.arraycopy(array, j, array, j + 1, i - j);
            array[j] = t;
        }
    }

    public static <T extends Comparable<T>> void shell(T[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3)
            h = h * 3 + 1;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (less(a[j], a[j - h]))
                        exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }


   /* public static void main(String[] args) {
        Integer[] a = {3, 4, 3, 5, 2, 1, 10};

        Derived[] d_list = {new Derived(3), new Derived(4), new Derived(3), new Derived(5), new Derived(2), new Derived(1)};
        insert(d_list);
        show(d_list);
        //select(a);
        //popup(a);
        shell(a);
        System.out.println(isSorted(a));
        show(a);
        Comparator<Number> numberComparator = (o1, o2) -> o1.intValue() - o2.intValue();
        List<Integer> l = Arrays.asList(3, 4, 3, 5, 2, 1, 10);
        Collections.sort(l, numberComparator);
        show(l);
    }*/
}
