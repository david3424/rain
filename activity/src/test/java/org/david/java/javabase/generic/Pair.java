package org.david.java.javabase.generic;

import org.junit.Test;


/**
 * Created by mac on 16/5/17.
 * 泛型测试*
 */
public class Pair<L, M, R> implements ToMap<M> {


    private L l;
    private M m;
    private R r;


    /*public Pair(L l,R r) {
        this.l = l;
        this.r = r;
    }*/

    public L getL() {
        return l;
    }

    public void setL(L l) {
        this.l = l;
    }

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

    public R getR() {
        return r;
    }

    public void setR(R r) {
        this.r = r;
    }

    public <T> T getObject(Class<T> c) throws IllegalAccessException, InstantiationException {
        return c.newInstance();
    }


    public <T extends Number> T getMax(T[] array) {
        T max = null;
        for (T element : array) {
            max = element.doubleValue() > (max != null ? max.doubleValue() : 0) ? element : max;
        }
        return max;
    }


    @Test
    public void testName() throws Exception {

        Pair<Integer, String, Float> pair = new Pair<>();
        pair.setL(111);
        pair.setR(0.2f);
        pair.setM("测试");
        System.out.println(pair);

        Pair tuple = pair.getObject(Pair.class);
        System.out.println(tuple);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "l=" + l +
                ", m=" + m +
                ", r=" + r +
                '}';
    }

    @Override
    public  M toMapMethod(M m) {
        return m;
    }
}
