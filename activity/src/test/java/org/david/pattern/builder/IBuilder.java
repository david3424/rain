package org.david.pattern.builder;

/**
 * Created by mac on 15-4-7.
 * 构造器接口，根据传入的T变化*
 */
public interface IBuilder<T>{
    
    public  T build();
}
