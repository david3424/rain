package effectivejava.ch4i17;

import java.util.*;

public final class Sub extends Super {
    private final Date date; // Blank final, set by constructor

    Sub() {
        System.out.println("======3======:" );
        date = new Date();
        System.out.println("======4======:" + date);
    }

    // Overriding method invoked by superclass constructor
    @Override public void overrideMe() {
        System.out.println("======2======:" + date);
    }

    public static void main(String[] args) {
        Super sub = new Sub();
        sub.overrideMe();
    }
}
