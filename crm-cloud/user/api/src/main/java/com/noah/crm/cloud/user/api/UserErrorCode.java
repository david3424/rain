package com.noah.crm.cloud.user.api;


import com.noah.crm.cloud.apis.exception.IErrorCode;

/**
 */
public enum UserErrorCode implements IErrorCode {

    UsernameExist(409, "用户名已存在"),
    PhoneExist(409, "手机已存在"),
    EmailExist(409, "邮箱已存在");

    private int status;

    private String message;

    UserErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }


    @Override
    public String getRespCode() {
        return this.name();
    }

    @Override
    public String getRespMsg() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
