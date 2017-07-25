package java151.item4;

/**
 *
 */
public class VaragsClient {

    public void calPrice(int price, int discount) {
        float knockdownPrice = price * discount / 100.0F;
        System.out.println("简单折扣后的价格是:" + knockdownPrice);
    }

    //复杂多折扣计算
    public void calPrice(int price, int... discounts) {
        float knockdownPrice = price;
        for (int discount : discounts) {
            knockdownPrice = knockdownPrice * discount / 100;
        }
        System.out.println("复杂折扣后的价格是:" + knockdownPrice);
    }

    public static void main(String[] args) {
        //向上转型
        Base base = new Sub();
        base.fun(100, 50);
        //不转型
        Sub sub = new Sub();
        sub.fun(100, new int[0]);
    }
}

//基类
class Base {
    void fun(int price, int... discounts) {
        System.out.println("Base……fun");
    }
}

//子类，覆写父类方法
class Sub extends Base {
    @Override
    void fun(int price, int[] discounts) {
        System.out.println("Sub……fun");
    }
}

