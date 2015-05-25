package org.david.java.classandmethod;

/**
 * Created by mac on 15-5-25.
 * 构造方法块：自动的将代码块中的内容加到各构造器的最前面（有this的除外）*
 * 其他的代码块：
 *  静态代码块 对象创建前环境初始化，静态变量等，static修饰
 *  普通方法代码块，必须通过方法名执行
 *  同步代码块：Synchronized，同一时间只能一个线程进入* *
 * * * * * 
 */
public class ConstructorBlock {
    public static void main(String[] args) {
        new MyBase();
        new MyBase("");
        new MyBase(0);
        System.out.println("实例对象数量：" + MyBase.getNumOfObjects());
    }
}

class MyBase{
    private static int numOfObjects = 0;

    {
        //构造代码块，计算产生对象数量
        numOfObjects++;
    }

    public MyBase(){
    }

    //有参构造调用无参构造
    public MyBase(String _str){
        this();
    }
    //有参构造不调用其他构造
    public MyBase(int _i){
    }

    //返回在一个JVM中，创建了多少个实例对象
    public static int getNumOfObjects(){
        return numOfObjects;
    }
}
