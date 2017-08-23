package java151.item3;

public class Client {
    public static void main(String[] args) {
        //调用接口的实现
//        B.s.doSomething();
        String tp = "xxx";
        String tp2 = "xxx";
        System.out.println(tp == "xxx");
        System.out.println(tp == tp2);
    }
}

//在接口中存在实现代码 匿名内部类 不建议xx
interface B {
    S s = new S() {
        public void doSomething() {
            System.out.println("我在接口中实现了");
        }
    };

}

//被实现的接口
interface S {
    void doSomething();
}
