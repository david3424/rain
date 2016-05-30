package org.david.java.javabase.jvm;

import org.junit.Test;

/**
 * Created by mac on 16/5/23.
 */
public class StackOOM {


    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();

    }

    public static void main(String[] args) throws Throwable {
        StackOOM stackOOM = new StackOOM();
        try {
            stackOOM.stackLeak();
        } catch (Throwable t) {
            System.out.println(stackOOM.stackLength);
            throw t;
        }
    }


    private void donotStop() {
        while (true) {
        }
    }


    /**
     * 开多线程，占用所有内存，看OOM情况，慎用，会卡死机器*
     */
    @Test
    public void TestStackLeakByThread() {

        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    donotStop();
                }
            });
            thread.start();
        }

    }

}
