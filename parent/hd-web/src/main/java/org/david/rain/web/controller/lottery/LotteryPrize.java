package org.david.rain.web.controller.lottery;


import org.david.rain.common.components.lottery.EventPrize;
import org.david.rain.common.entity.HdTable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-14
 * Time: 上午11:43
 * To change this template use File | Settings | File Templates.
 */
@HdTable("lottery_demo_prize")
public class LotteryPrize extends EventPrize {

    protected Integer id;
    protected Integer userId;
    protected Long roleId;
    protected String roleName;
    protected Integer server;
    protected String serverName;
    protected String ip;
    protected Integer type;


    public LotteryPrize() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
