package org.david.rain.monitor.monitor.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 13-7-24
 * Time: 下午3:11
 */
public class Task {
    private Integer id;
    private Integer user_id;
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Task() {
    }


    @Override
    public String toString() {
        return "Task{" +
                "user_id=" + user_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
