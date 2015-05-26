package org.david.java.classandmethod.visable;

import com.google.common.base.Objects;

/**
 * Created by mac on 15-5-26.
 * 
 * 成功更改了一个不可变数组，把成员GREEN修改为YELLO* *
 */
public class UnmodifiableArray {

    // 潜在安全漏洞
    public static final String[] VALUES = { "RED", "GREEN" };

    public static void main(String[] args) {
        UnmodifiableArray UF = new UnmodifiableArray();
        UF.VALUES[1] = "YELLO";//设置final数组成员
        System.out.println(UF);
    }
    //使用Guava
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("VALUES0", UnmodifiableArray.VALUES[0])
                .add("VALUES1", UnmodifiableArray.VALUES[1]).toString();
    }
}
