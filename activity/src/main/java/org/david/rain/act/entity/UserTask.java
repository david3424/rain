package org.david.rain.act.entity;

/**
 * Created by mac on 14-11-30.
 */
public class UserTask {

    private String title;
    private String login_name;
    private Long user_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserTask{" +
                "title='" + title + '\'' +
                ", login_name='" + login_name + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
