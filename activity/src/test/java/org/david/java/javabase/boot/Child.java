package org.david.java.javabase.boot;

/**
 * Created by david on 2014/11/12.
 * 启动顺序分析
 */
public class Child extends Parent {
    private static String str = "Hello";

    static {//静态块
        System.out.println(str);//父类初始化静态块后执行(4)
        System.out.println("Init child static block");
    }

    {//普通程序块
        System.out.println("Init child program block"); //父类执行构造函数后执行(6)
    }

    public Child() {
        super(); //默认先执行父类构造函数(1)
        System.out.println("Init child structure");//子类构造函数（7）
    }

    public static void main(String args[]) {
        Child c = new Child();
        System.out.println(c.method());
        System.out.println("-----I am a cut-off line----");
        Child c1 = new Child();//静态变量只初始化一次
        System.out.println(c1.method());
        /*分析：初始化顺序：父类静态变量->父类静态快->子类静态变量->子类静态块->父类程序块->父类构造函数->子类程序块->子类构造函数
        结果输出
        10
        Init parent static block
        Hello
        Init child static block
        Init parent program block
        Init parent structure
        Init child program block
        Init child structure
        11
        -----I am a cut-off line----
        Init parent program block
        Init parent structure
        Init child program block
        Init child structure
        12
        * */
    }
}