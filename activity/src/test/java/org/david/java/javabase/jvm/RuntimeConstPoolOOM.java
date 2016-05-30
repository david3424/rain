package org.david.java.javabase.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/5/23.
 * -XX:PermSize=4m -XX:MaxPermSize=4m*
 */
public class RuntimeConstPoolOOM {


    /**
     * 常量池属于PerGen 方法区溢出*
     * @param args
     */
    public static void main(String[] args) {

        List<String> lists = new ArrayList<>();
        String vv = "9223372036854775807922337203685477580792233720368547758079223372036854775807922337203685477580792233720368547758079223372036854775807922337203685477580792233720368547758079223372036854775807922337203685477580792233720368547758079223372036854775807";
        while (true) {
            lists.add(vv.intern());
            System.out.println(lists.size());
        }
    }
}