package java151.item3;

/**
 *
 */
public class ConstractSimpleClient {


    public static void main(String[] args) {
        Son s = new Son();
        s.doSomething();
    }
}

//父类
class Father {
    Father() {
        new Other();
    }
}

//子类
class Son extends Father {
    public void doSomething() {
        System.out.println("Hi,show me something");
    }
}

//相关类
class Other {
    public Other() {
        new Son();
    }

}

