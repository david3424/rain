package org.david.pattern.adapter;// Concrete implementation built atop skeletal implementation - Page 95

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

/**
 * 适配器模式类似：把int数组适配成Integer实例列表List
 * 匿名内部类，自动拆箱装箱效率差* * 其实就是定义一个AbstractList的子类，组合引用数组a[]，返回标准的List
 * 方法*
 */
public class IntArrays {
    static List<Integer> intArrayAsList(final int[] a) {
        if (a == null)
            throw new NullPointerException();

        return new AbstractList<Integer>() { //扩展抽象骨架类
            public Integer get(int i) {
                return a[i];  // Autoboxing (Item 5)
            }

            @Override public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // Auto-unboxing
                return oldVal;  // Autoboxing
            }

            public int size() {
                return a.length;
            }
        };
    }

    public static void main(String[] args) {
        int[] a = new int[10];
        for (int i = 0; i < a.length; i++)
            a[i] = i;
        List<Integer> list = intArrayAsList(a);

        Collections.shuffle(list);
        System.out.println(list);
    }
}
