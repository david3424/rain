package org.david.java.classandmethod.inheritanceandcompact;// Broken - Inappropriate use of inheritance!

import java.util.*;

/**
 * 因为抽象类的说明不全，这里的count会被重复计算，解决办法是利用装饰模式，绕过直接继承*
 * @param <E>
 */
public class InstrumentedHashSet<E> extends HashSet<E> {
    // The number of attempted element insertions
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSet<String> s =
            new InstrumentedHashSet<String>();
        s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));    
        System.out.println(s.getAddCount());
    }
}
