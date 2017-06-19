package net.jcip.test;

import net.jcip.examples.UnsafeCountingFactorizer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xdw9486 on 2016/12/26.
 */
public class TestCase {

    ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Test
    public void testSequenceCase() throws Exception {

        TestRunnable tt = new TestRunnable();
        for (int i = 0; i < 2000; i++) {
            executorService.submit(tt);
        }
        Thread.sleep(2000);
        System.out.println(tt.getTempMap().get("count"));
        executorService.shutdown();

    }


    /*  private final HashMap<String, String> map = new HashMap<>(2);
    private final Map<String, String> wordCounts = new ConcurrentHashMap<>();
    private final Hashtable<String, String> hashTable = new Hashtable<>();

    @Test
    public void testHashMap() throws Exception {


        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString(), "");
                    wordCounts.put(UUID.randomUUID().toString(), "");
                    hashTable.put(UUID.randomUUID().toString(), "");
                }
            }, "ftf" + i).start();
        }

        Thread.sleep(10000);
        System.out.println(map.size());
        System.out.println(wordCounts.size());
        System.out.println(hashTable.size());

       *//* Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }, "ftf");
        t.start();
        t.join();*//*

    }*/


}
