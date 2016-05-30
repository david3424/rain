package org.david.java.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mac on 16/5/17.
 * Delegation实现：MAP属性，用匿名内部类初始化覆盖自带方法*
 * 会把最先加入的删掉，也就是最上面的，迭代不会影响排序，Entry是new出来的*
 */
public class LRUDelegation<K, V> {

    private static final long serialVersionUID = 1L;
    LinkedHashMap<K, V> map;
    protected int maxElements;


    /**
     * 覆盖原构造函数，设置访问顺序排序（accessOrder=true）初始化MAP大小、缓存大小*
     *
     * @param cacheSize
     */
    public LRUDelegation(int cacheSize) {
        //根据cacheSize和加载因子计算hashmap的capactiy，+1确保当达到cacheSize上限时不会触发hashmap的扩容，
        int capacity = (int) Math.ceil(cacheSize / 0.75f) + 1;
        map = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            private static final long serialVersionUID = -4640004977337546890L;

            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxElements;
            }
        };
        maxElements = cacheSize;
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void remove(K key) {
        map.remove(key);
    }

    public synchronized Set<Map.Entry<K, V>> getAll() {
        return map.entrySet();
    }

    public synchronized int size() {
        return map.size();
    }

    public synchronized void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {

        LRUDelegation<String, String> c = new LRUDelegation<>(3);
        c.put("1", "one"); // 1
        c.put("2", "two"); // 2 1
        c.put("3", "three"); // 3 2 1
        c.put("4", "four"); // 4 3 2
        if (c.get("2") == null)
            throw new Error(); // 2 4 3
        c.put("5", "five"); // 5 2 4
        c.put("4", "second four"); // 4 5 2
        // Verify cache content.
        if (c.size() != 3)
            throw new Error();
        if (!c.get("4").equals("second four"))
            throw new Error();
        if (!c.get("5").equals("five"))
            throw new Error();
        if (!c.get("2").equals("two")) //2 5 4
            throw new Error();
        // List cache content.
        for (Map.Entry<String, String> e : c.getAll())
            System.out.println(e.getKey() + " : " + e.getValue());
        c.put("1", "one");//1 2 5
        System.out.println(c);
    }

}
