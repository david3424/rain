package org.david.java.classandmethod;

/**
 * 覆盖来增强或减弱父类的行为，只针对非静态方法，使用注解@override*
 * 隐藏是指类方法（静态方法）的重写，一般是用类名即表面类型调用，不建议隐藏静态方法
 * *
 * Created by mac on 15-5-25.
 */
public class StaticClient {
    public static void main(String[] args) {
        Base base = new Sub();
        //调用非静态方法
        base.doAnything();
        //调用静态方法
        base.doSomething();
    }
}

class Base{
    //父类静态方法
    public static void doSomething(){
        System.out.println("我是父类静态方法");
    }
    //父类非静态方法
    public void doAnything(){
        System.out.println("我是父类非静态方法");
    }
}

class Sub extends Base {
    //子类同名、同参数的静态方法

    public static void doSomething() {
        System.out.println("我是子类静态方法");
    }

    //重写父类的非静态方法
    @Override
    public void doAnything() {
        System.out.println("我是子类非静态方法");
    }

}
