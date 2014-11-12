package org.david.rain.javabase.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangxingang on 14-10-22.
 */
public class SubListTest {



    @Test
            public void subListTest(){
    List<Integer> initData = Collections.nCopies(100,0);
    ArrayList<Integer> list = new ArrayList<>(initData);
        list.subList(20,30).clear();
        System.out.println("list is " + list);
    }
}
