package org.david.rain.monitor.monitor.api;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-5-7
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class ApiOscillDateItem {
    private String itemNameCh;
    private String changeTime;
    private ApiUser user;

    public String getItemNameCh() {
        return itemNameCh;
    }

    public void setItemNameCh(String itemNameCh) {
        this.itemNameCh = itemNameCh;
    }

    public ApiUser getUser() {
        return user;
    }

    public void setUser(ApiUser user) {
        this.user = user;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
