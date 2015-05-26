package org.david.pattern.builder;


/**
 * 多个参数时，利用构造器模式
 * 构建器是个内部静态类，由必要参数生成构造函数，进一步调用类setter方法返回自身形成链式，最后调用无参build方法（客户端自身全参构造函数）生成不可变对象*
 * 不直接生成想要的对象，客户端利用所有必要的参数调用构造器，得到一个builder对象，然后客户端调用类似setter方法* *
 * Created by mac on 15-4-7.
 */
public class PersonalInfo {

    private final String username;
    private final int userid;
    private final String address;
    private final int age;

    public static class PersonalInfoBuilder implements IBuilder<PersonalInfo>{
//        必选参数
        private final String username;
        private final int userid;

//        可选参数，所以给个初始值
        private  String address = null;
        private  int age = 0 ;


        public PersonalInfoBuilder(String username,int userid){
            this.username = username;
            this.userid = userid;
        }

        private PersonalInfoBuilder age(int age){
            this.age = age;
            return  this;
        }

        PersonalInfoBuilder address(String address){
            this.address = address;
            return this;
        }

       public PersonalInfo build(){
            return new PersonalInfo(this);
        }
    }

        private PersonalInfo(PersonalInfoBuilder builder){
        username = builder.username;
        userid = builder.userid;
        age = builder.age;
        address = builder.address;
    }

    @Override
    public String toString() {
        return "Mybuilder{" +
                "username='" + username + '\'' +
                ", userid=" + userid +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        PersonalInfo personalInfonfo = new  PersonalInfoBuilder("david",100054).age(30).address("北京").build();
        System.out.println(personalInfonfo);
    }
    
}
