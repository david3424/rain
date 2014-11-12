package org.david.rain.javabase.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxingang on 14-10-27.
 */
public class ConllectionsMethod {


    public static void main(String[] args) {
      /*  List<String> cities = new ArrayList<String>();
        cities.add("上海");
        cities.add("广州");
        cities.add("广州");
        cities.add("北京");
        cities.add("天津");
        //indexOf方法取得索引值
        int index1 = cities.indexOf("广州");
        //binarySearch查找到索引值
        int index2 = Collections.binarySearch(cities, "广州");
        System.out.println("索引值(indexOf)："+index1);
        System.out.println("索引值（binarySearch)："+index2);*/


        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("B");

        //并集
        list1.addAll(list2);

        //交集
//               list1.retainAll(list2);
//               for(String str:list1){
//                       System.out.println(str);
//               }
//
        //补集
       /* list1.removeAll(list2);
        for(String str:list1){
            System.out.println(str);
        }*/

        //无重复并集
//              list2.removeAll(list1);
//              list1.addAll(list2);
              for(String str:list1){
                      System.out.println(str);
              }
    }
}
