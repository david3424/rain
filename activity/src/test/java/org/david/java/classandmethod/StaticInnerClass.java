package org.david.java.classandmethod;

/**
 * Created by mac on 15-5-25.
 */
public class StaticInnerClass {
    
    
    private  String name;
    private  Home home;
    private House house;

    public StaticInnerClass(String name) {
        this.name = name;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }


    @Override
    public String toString() {
        return "StaticInnerClass{" +
                "name='" + name + '\'' +
                ", home=" + home +
                '}';
    }

    /**
     *静态内部类:封装性、代码可读性；可以脱离外部类存在
     * 不持有外部类引用；不依赖外部类；可以声明static的变量与方法(常量都一样可以) *  *
     */
    public static class Home {
        
        private  String address;
        private  String tel;

        public Home(String address, String tel) {
            this.address = address;
            this.tel = tel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        @Override
        public String toString() {
            return "Home{" +
                    "address='" + address + '\'' +
                    ", tel='" + tel + '\'' +
                    '}';
        }
    }



    private class House {
        
        private String phone;
        private Integer age;

        public String getPhone() {
            return phone + home.getTel();
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getAge() {
            return age;
        }
        

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}



class StaticInnerClassTest {


    public static void main(String[] args) {
        StaticInnerClass staticInnerClass = new StaticInnerClass("david");
        staticInnerClass.setHome(new StaticInnerClass.Home("北京","138"));
        System.out.println(staticInnerClass);
    }
}



