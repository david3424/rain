package org.david.rain.wmeovg.request.bean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.david.rain.wmeovg.request.alg.Signature;
import org.david.rain.wmeovg.request.util.Priority;
import org.david.rain.wmeovg.request.util.WmeovgProperties;


/**
 * @version 1.0
 * @ClassName PrizeBean
 * @Description 发奖请求参数实体
 * @date 2010-8-5 下午06:01:45
 */
public class PrizeBean implements Serializable {

    private String cid; // 客户端标识
    private String gid; // 流水号
    private String appid; // 流水号
    private Integer zoneid;
    private String account;
    private String rolename;
    private Long roleid;
    private Integer prizeid;
    private Integer count = 1; // 发送个数，默认发送1个
    private Priority priority;
    private String callback; // 附加参数

    /**
     * 带部分默认值的构造函数（发送数目默认为1个，发送优先级为普通优先级）
     *
     * @param gid      流水号
     * @param zoneid   游戏服务器ID
     * @param account  通行证账号
     * @param rolename 游戏角色名
     * @param prizeid  奖品AU编号
     * @param callback 客户端请求附加信息
     * @throws Exception
     */
    public PrizeBean(String gid, Integer zoneid, String account, String appid, Long roleid,
                     Integer prizeid, String callback) throws Exception {
        super();
        this.zoneid = zoneid;
        this.account = account.trim();
        this.appid = appid;
        this.setRoleid(roleid);
        this.prizeid = prizeid;
        this.callback = Signature.encryptBASE64(callback.trim().getBytes("UTF-8"));
        this.count = 1;  // 默认每次发送一个
        this.priority = Priority.NORMAL; // 默认普通优先级发送

        this.cid = WmeovgProperties.getCid();
        this.gid = gid;
    }

    /**
     * 构造函数
     *
     * @param gid      流水号
     * @param zoneid   游戏服务器ID
     * @param account  通行证账号
     * @param rolename 游戏角色名
     * @param prizeid  奖品AU编号
     * @param count    发送数目
     * @param priority 发送优先级
     * @param callback 客户端请求附加信息
     * @throws Exception
     */
    public PrizeBean(String gid, Integer zoneid, String account, String rolename, Long roleid,
                     Integer prizeid, Integer count, Priority priority, String callback) throws Exception {
        super();
        //System.out.println("=============" + callback);
        this.zoneid = zoneid;
        this.account = account.trim();
        if ((rolename = StringUtils.trimToNull(rolename)) != null)
            this.rolename = Signature.encryptBASE64(rolename.getBytes("UTF-8"));
        this.setRoleid(roleid);
        this.prizeid = prizeid;
        this.count = count;
        this.priority = priority;
        this.callback = Signature.encryptBASE64(callback.trim().getBytes("UTF-8"));

        this.cid = WmeovgProperties.getCid();
        this.gid = gid;
    }

    public String getCid() {
        return cid;
    }


    public void setCid(String cid) {
        this.cid = cid;
    }


    public String getGid() {
        return gid;
    }


    public void setGid(String gid) {
        this.gid = gid;
    }


    public Integer getZoneid() {
        return zoneid;
    }


    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
    }


    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account;
    }


    public String getRolename() {
        return rolename;
    }


    public void setRolename(String rolename) {
        this.rolename = rolename;
    }


    public Integer getCount() {
        return count;
    }


    public void setCount(Integer count) {
        this.count = count;
    }


    public Integer getPrizeid() {
        return prizeid;
    }


    public void setPrizeid(Integer prizeid) {
        this.prizeid = prizeid;
    }


    public Priority getPriority() {
        return priority;
    }


    public void setPriority(Priority priority) {
        this.priority = priority;
    }


    public String getCallback() {
        return callback;
    }


    public void setCallback(String callback) {
        this.callback = callback;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getRoleid() {
        return roleid;
    }

    public String getAppid() {
        
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     *
     */
    private static final long serialVersionUID = 2603084990988881878L;
}
