package org.david.java.javabase.innerclass;

/**
 * Created by mac on 14-11-13.
 */
public class TestInner {


    public InnerTest test() {
        return new InnerTest() {
            @Override
            public int fly() {
                return 1000;
            }
            @Override
            public String fly1() {
                return "ddd";
            }
        };
    }
    public static void main(String[] args) {
         final String mustFinalVar = "xxxx";
TestInner testInner = new TestInner();
        InnerTest innerTest = testInner.test();
        testInner.test();

    }

}
