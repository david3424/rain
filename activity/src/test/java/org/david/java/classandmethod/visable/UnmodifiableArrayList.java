package org.david.java.classandmethod.visable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by mac on 15-5-26.
 * *
 */
public class UnmodifiableArrayList {

    // private 消除安全漏洞
    private static final String[] PRIVATE_VALUES = { "RED", "GREEN" };
    public static final List<String> mmutableList = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
//      public static final   ImmutableList mmutableList = ImmutableList.of(PRIVATE_VALUES);
    public static void main(String[] args) {
        UnmodifiableArrayList UF = new UnmodifiableArrayList();
        UF.mmutableList.add(1, "YELLO"); //会抛出UnsupportedOperationException异常
        System.out.println(UF);
    }
    //使用Guava
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("PVALUES0", UnmodifiableArrayList.mmutableList.get(0))
                .add("PVALUES1", UnmodifiableArrayList.mmutableList.get(1))
                .toString();
    }
}


//构建一个不可变集合，Guava给出了更简洁的办法：
//  ImmutableList mmutableList = ImmutableList.of(PRIVATE_VALUES);

