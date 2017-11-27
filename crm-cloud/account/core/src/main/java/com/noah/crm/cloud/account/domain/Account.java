package com.noah.crm.cloud.account.domain;


import com.noah.crm.cloud.common.domain.AbstractVersionEntity;

import javax.persistence.*;

/**
 * @author xdw9486
 */
@Entity
@Table(name = "account")
public class Account extends AbstractVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long balance;

    @Column
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
