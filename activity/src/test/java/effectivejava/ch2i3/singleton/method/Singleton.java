package effectivejava.ch2i3.singleton.method;

/**
 * Created by mac on 15-4-7.
 */
public class Singleton {
    
//    私有域
    private static final  Singleton singleton = null ;
//    私有构造
    private Singleton(){}
    
//    公有方法 饿汉模式 如果提前new，就是懒汉模式
    public  static Singleton newInstance(){
        if(singleton == null){
            return new Singleton();
        }else{
            return singleton;
        }
    }
}
