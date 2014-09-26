package org.david.rain.entity;

/**
 * Created by david on 2014/8/21.
 * test for jackson
 */
public class Birthday {

    private String birthday;

    public Birthday(String birthday) {
        this.birthday = birthday;
    }

    //getter、setter


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Birthday() {}

    @Override
    public String toString() {
        return this.birthday;
    }
}
