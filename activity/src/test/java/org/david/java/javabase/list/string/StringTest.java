package org.david.java.javabase.list.string;

import org.junit.Test;

/**
 * Created by mac on 16/4/28.
 * 测试相关方法*
 */
public class StringTest {


    /*算法：先获取长度，取小，循环value(char数组）比对，如果都一样（子串），返回length差值
    * 首字母字典顺序比较，不相等就返回首字母char值的差，如果是子串，就返回长度的差* */
    @Test
    public void TestCompareTo() {

        String str1 = "Strings are immutable";
        String str2 = "Strings are immutablethanks!!";
        String str3 = "Integers are not immutable";

        int result = str1.compareTo(str2);
        System.out.println(result);

        result = str2.compareTo(str3);//就是S与I的比较
        System.out.println(result);

        result = str3.compareTo(str1);
        System.out.println(result);
    }

}
