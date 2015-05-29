package org.david.rain.web.init.servlet.captcha;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-13
 * Time: 上午11:48
 * To change this template use File | Settings | File Templates.
 */
public class Characters {

    private Integer id;
    private String character;
    private String firstAlphabet;
    private String lastAlphabet;
    private String quanpin;


    public Characters() {
    }

    public Characters(String character, String firstAlphabet, String lastAlphabet, String quanpin) {
        this.character = character;
        this.firstAlphabet = firstAlphabet;
        this.lastAlphabet = lastAlphabet;
        this.quanpin = quanpin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getFirstAlphabet() {
        return firstAlphabet;
    }

    public void setFirstAlphabet(String firstAlphabet) {
        this.firstAlphabet = firstAlphabet;
    }

    public String getLastAlphabet() {
        return lastAlphabet;
    }

    public void setLastAlphabet(String lastAlphabet) {
        this.lastAlphabet = lastAlphabet;
    }

    public String getQuanpin() {
        return quanpin;
    }

    public void setQuanpin(String quanpin) {
        this.quanpin = quanpin;
    }

    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            System.out.println(new Random().nextInt(3));
        }
    String a="我爱中国";
        for(int i=0;i<a.length();i++){
            System.out.println(a.substring(i,i+1));
        }
    }
}
