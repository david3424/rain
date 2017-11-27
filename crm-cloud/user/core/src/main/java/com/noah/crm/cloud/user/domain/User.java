package com.noah.crm.cloud.user.domain;


import com.noah.crm.cloud.common.domain.AbstractVersionEntity;

import javax.persistence.*;

/**
 * @author xdw9486
 */
@Entity
@Table(name = "user")
public class User extends AbstractVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
