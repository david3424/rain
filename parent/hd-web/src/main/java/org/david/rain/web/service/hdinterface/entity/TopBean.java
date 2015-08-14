package org.david.rain.web.service.hdinterface.entity;


import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public class TopBean implements Serializable {
    private Integer number;
    private String username;
    private Integer score;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


}
