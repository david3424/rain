package org.david.java.javabase.list.map;

import org.junit.Test;

import java.util.*;

/**
 * Created by mac on 16/4/28.
 * test comparator of map*
 */
public class TreeMapTest {


    /*按照KEY逆序排序，默认是KEY的升序
        算法：newmap的时候传入匿名内部类Comparator，实现compare方法，put的时候会按照Comparator的实现来比较*/
    @Test
    public void TestSort() {

        Map<String, String> map = new TreeMap<>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
        getTestMap(map);

        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }
    
    /*按照value来排序
    算法：利用Collections的sort(List<T> list, Comparator<? super T> c)方法
    HashMap等其他集合可参照这个，先转为ilst，利用comarator升序或降序，前提是必须泛型内是支持排序的
    * * */

    @Test
    public void testSortByValue() throws Exception {
        Map<String, String> map = new TreeMap<>();
        getTestMap(map);
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            //升序排序
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, String> mapping : list) {
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }

    }

    public void getTestMap(Map<String, String> map) {

        map.put("c", "2ccccc");
        map.put("a", "4aaaaa");
        map.put("b", "3bbbbb");
        map.put("d", "1ddddd");
    }

    @Test
    public void testName() throws Exception {

        Random r = new Random() ;
        System.out.println(r.nextInt(10));
        System.out.println(r.nextInt(10));
        System.out.println(r.nextInt(10));
    }
}
