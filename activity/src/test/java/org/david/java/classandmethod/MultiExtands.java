package org.david.java.classandmethod;

/**
 * Created by mac on 15-5-25.
 */
public class MultiExtands {
}


//父亲
interface Father{
    public int strong();
}

//母亲
interface Mother{
    public int kind();
}
class FatherImpl implements Father{
    //父亲的强壮指数是8
    public int strong(){
        return 8;
    }
}

class MotherImpl implements Mother{
    //母亲的温柔指数是8
    public int kind(){
        return 8;
    }
}

/**
 * 继承父亲，实现母亲的特征* 
 */
class Son extends FatherImpl implements Mother{
    @Override
    public int strong(){
        //儿子比父亲强壮
        return super.strong() + 1;
    }

    @Override
    public int kind(){
        
        return new MotherSpecial().kind();
        //匿名内部类一样可以实现
      /*  return new MotherImpl(){
            @Override
        public int kind(){
                return super.kind()-1;
            }
        }.kind();*/
    }

    /**
     * 实例内部类* 
     */
    private class MotherSpecial extends MotherImpl{
        @Override
        public int kind(){
            //儿子温柔指数降低了
            return super.kind() - 1;
        }
    }
}

class Daughter extends MotherImpl implements Father{

    @Override
    public int strong() {
        return new FatherImpl(){
            @Override
            public int strong() {
                //女儿的强壮指数降低了
                return super.strong() - 2 ;
            }
        }.strong();
    }
    
    public int strongs (){
        return new Father(){
            @Override
        public int strong(){
                return 12;
            }
        }.strong();
    }


}
