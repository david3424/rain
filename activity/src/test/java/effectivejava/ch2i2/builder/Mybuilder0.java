package effectivejava.ch2i2.builder;

/**
 * 多个参数时，虽然可以用重叠模式，多个参数会失控,灵活性也差
 * javaBean同样有问题，也可能出现不一致，也没法把类做成不可变的* *
 * Created by mac on 15-4-7.
 */
public class Mybuilder0 {

    private final String username;
    private final int userid;
    private final String address;
    private final int age;

    public Mybuilder0(String username, int userid) {
        this(username, userid, 0);

    }

    public Mybuilder0(String username, int userid, int age) {
        this(username, userid, age, "");

    }

    public Mybuilder0(String username, int userid, int age, String address) {
        this.username = username;
        this.userid = userid;
        this.address = address;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Mybuilder0{" +
                "username='" + username + '\'' +
                ", userid=" + userid +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        Mybuilder0 mybuilder0 = new Mybuilder0("david",100054);
        Mybuilder0 mybuilder00 = new Mybuilder0("david",100054,1);
        System.out.println(mybuilder0);
    }
}
