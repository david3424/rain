package com.wanmei.entity;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@HdTable("ss_user")
public class User extends IdEntity {
    private String login_name;
    private String name;
    @ThisIgnore
    private String plainPassword;
    private String password;
    private String salt;
    private String roles;
    private Date register_date;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 不持久化到数据库，也不显示在Restful接口的属性.
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    /* public List<String> getRoleList() {
        // 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
        return ImmutableList.copyOf(StringUtils.split(roles, ","));
    }*/

    // 设定JSON序列化时的日期格式


    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}