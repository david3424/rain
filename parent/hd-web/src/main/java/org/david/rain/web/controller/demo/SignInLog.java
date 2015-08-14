package org.david.rain.web.controller.demo;


import org.david.rain.common.components.util.ActionUtil;
import org.david.rain.common.entity.HdTable;
import org.david.rain.common.util.DateUtils;

@HdTable(WebMallConst.TABLE_SIGN_IN_LOG)
public class SignInLog {
    private Integer id;
    private String username;
    private String date;
    private Integer status;
    private String createtime;
    private String ip;

    public SignInLog() {
    }

    public SignInLog(String username,String date) {
        this.username = username;
        this.createtime = DateUtils.getCurrentFormatDateTime();
        this.date=date;
        this.status = 0;
        this.ip = ActionUtil.getRealIp();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
