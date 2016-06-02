package org.david.java.algorithm.sort;

/**
 * Created by roverll on 6/1/16.
 */
public class BaseC implements Comparable<BaseC> {

    protected int value;

    public BaseC(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(BaseC o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "BaseC{" +
                "value=" + value +
                '}';
    }
}
