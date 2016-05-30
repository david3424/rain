package org.david.java.javabase.jvm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/5/23.
 * 测试堆内存溢出
 * vm args: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError* *
 */
public class HeapOOM {

    @Test
    public void testHeapOOM() {

        List<HeapOOM> mm = new ArrayList<>();
        while (true) {
            mm.add(new HeapOOM());
        }
    }
}
