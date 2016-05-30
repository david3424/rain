package org.david.java.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mac on 16/5/17.
 * inheritance实现：继承map，利用其自带方法实现*
 * 把最近一次使用时间离现在时间最远的数据删除掉*  ArrayList的话每次访问放入List的一端，满了就T掉最远端的，但是效率低，需要移位，O(n),而MAP每次访问后会把元素放入链表的底端，这样每次删除顶端元素就行。
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;
    protected int maxElements;

    /**
     * 覆盖原构造函数，设置访问顺序排序（accessOrder=true）初始化MAP大小、缓存大小*
     *
     * @param cacheSize
     */
    public LRULinkedHashMap(int cacheSize) {
        super(cacheSize, 0.75f, true);
        maxElements = cacheSize;
    }


    /**
     * 当MAP元素数量大于缓存大小，删除最近最少访问元素*
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return (size() > this.maxElements);
    }
}
