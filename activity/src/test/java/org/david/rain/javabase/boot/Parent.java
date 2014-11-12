package org.david.rain.javabase.boot;

/**
 * Created by david on 2014/11/12.
 * 测试用 父类
 */
public class Parent {
    private static int i = 10;//先初始化静态变量(2)

    static {
        System.out.println(i);//初始化静态块(3)
        System.out.println("Init parent static block");
    }

    {
        System.out.println("Init parent program block");//子类静态块后执行（5）
    }

    public Parent() {
        System.out.println("Init parent structure");//所有程序块后执行（6）
    }

    public int method() {
        return ++i;
    }
}
