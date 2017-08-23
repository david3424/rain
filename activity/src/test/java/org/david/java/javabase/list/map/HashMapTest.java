package org.david.java.javabase.list.map;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                sb.append("堆内存大小：" + total + "M\n");
                long free = rt.freeMemory() >> 20;
                sb.append("空闲内存：" + free + "M");
                System.out.println(sb);
            }
        });
        // 放入40万键值对
        for (int i = 0; i < 60 * 10000; i++) {
            System.out.println(i);
            map.put("key" + i, "vlaue" + i);
        }

     /*   List<String> list = new ArrayList<>();
        final Runtime rt = Runtime.getRuntime();
        // JVM终止前记录内存信息
        rt.addShutdownHook(new Thread() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                long heapMaxSize = rt.maxMemory() >> 20;
                sb.append("最大可用内存：").append(heapMaxSize).append("M\n");
                long total = rt.totalMemory() >> 20;
                sb.append("堆内存大小：").append(total).append("M\n");
                long free = rt.freeMemory() >> 20;
                sb.append("空闲内存：").append(free).append("M");
                System.out.println(sb);
            }
        });
        // 放入40万同样字符串
        for (int i = 0; i < 502654; i++) {
            try {
                list.add("key" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add("vlaue" + i);
        }*/

    }


    public void testSort(int n) {
        List<String> list = new ArrayList<>();
        while (n > 0) {
            list.add("test" + n--);
        }
        Collections.sort(list);
    }


    @Test
    public void OOMList() throws Exception {

    }
}