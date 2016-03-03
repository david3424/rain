package org.david.rain.monitor.monitor.api;

/**
 * Created by czw on 2014/4/23.
 */
public class ApiUser {

    public ApiUser(){

    }

    public ApiUser(String chName){
       this.setChName(chName);
    }

    private String chName;

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }



    @Override
    public String toString() {
        return "ApiUser{" +
                "chName='" + chName + '\'' +
                '}';
    }
}
