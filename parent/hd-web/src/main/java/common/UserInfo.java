
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
    public int userId = 0;
    /**
     * 操作者名称
     */
    public String userName = "";
    /**
     * 操作者IP
     */
    public String ip = "";


    public void dump() {
        dump(System.out);
    }

    public void dump(java.io.PrintStream ps) {
        ps.println("userId = " + userId);
        ps.println("userName = " + userName);
        ps.println("ip = " + ip);
    }
}
