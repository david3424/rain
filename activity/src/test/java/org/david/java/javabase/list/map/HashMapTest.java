package org.david.java.javabase.list.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 14-10-27.
 * *
 */
public class HashMapTest {


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        final Runtime rt = Runtime.getRuntime();
        // JVM终止前记录内存信息
        rt.addShutdownHook(new Thread() {
            @Override
            public void run() {
//                System.out.println("最大可用内存2进制:" + Long.toBinaryString(rt.maxMemory()));
                StringBuffer sb = new StringBuffer();
                long heapMaxSize = rt.maxMemory() >> 20;
//                System.out.println("最大可用内存右移后2进制:" + Long.toBinaryString(heapMaxSize));
                sb.append("最大可用内存：" + heapMaxSize + "M\n");
                long total = rt.totalMemory() >> 20; // 右移就是除以2^10次方，从bit换算到M
                sb.append("对内存大小：" + total + "M\n");
                long free = rt.freeMemory() >> 20;
                sb.append("空闲内存：" + free + "M");
                System.out.println(sb);
            }
        });
        // 放入40万键值对
        for (int i = 0; i < 60 * 10000; i++) {
            map.put("key" + i, "vlaue" + i);
        }
    }
}
