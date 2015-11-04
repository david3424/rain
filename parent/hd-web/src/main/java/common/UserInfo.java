
package common;

import java.io.Serializable;

/**
 * bean UserInfo
 * <p/>
 * 用户日志信息
 */
public class UserInfo implements Serializable {

    public UserInfo() {
    }

    public UserInfo(int _userId, String _userName, String _ip) {
        userId = _userId;
        userName = _userName;
        ip = _ip;
    }

    /**
     * 操作者ID
     */
    private int userId = 0;
    /**
     * 操作者名称
     */
    private String userName = "";
    /**
     * 操作者IP
     */
    private String ip = "";

    private String roleName;
    private String serverName;
    private Integer server;
    private Long roleId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
