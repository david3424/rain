package java151.item1;

import java.util.Random;

/**
 * 常量变成了变量，不可取
 */
public class ClientConstant {
    /*接口常量*/
    interface Const {
        //这还是常量吗？
        int RAND_CONST = new Random().nextInt(1000);
    }

    public static void main(String[] args) {

        System.out.println(Const.RAND_CONST);
    }
}

