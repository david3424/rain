package net.jcip.test;

import net.jcip.examples.CountingFactorizer;
import net.jcip.examples.Sequence;
import net.jcip.examples.UnsafeCountingFactorizer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xdw9486 on 2016/12/26.
 * 测试多线程例子
 */
public class TestRunnable implements Runnable {

    private Sequence sequence = new Sequence();
    UnsafeCountingFactorizer unsafeCountingFactorizer = new UnsafeCountingFactorizer();
    CountingFactorizer countingFactorizer = new CountingFactorizer();
    //    private Map<String, Integer> tempMap = new HashMap<>();
    private Map<String, Integer> tempMap = new ConcurrentHashMap<>();

    @Override
    public void run() {

        /*Integer va = sequence.getNext();
        if (tempMap.containsKey(va)) {
            tempMap.put(va, tempMap.get(va) + 1);
            System.out.println("cp the thread " + Thread.currentThread().getName() +",and va is " + va);
        } else {
            tempMap.put(va, 1);
        }
        System.out.println(Thread.currentThread().getName() + "线程被调用了。--" + va);*/
        countingFactorizer.service(new MockHttpServletRequest(), new MockHttpServletResponse());
    }

    public Map<String, Integer> getTempMap() {
        tempMap.put("count", (int) countingFactorizer.getCount());
        return tempMap;
    }
}
