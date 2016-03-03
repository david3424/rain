package org.david.rain.monitor.monitor.api;

/**
 * Created by czw on 2014/4/23.
 */
public class ApiDataItem {
    private Integer id;
    private String dataSource;
    private String changeTime;
    private String itemName;
    private String itemNameCh;
    private ApiUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

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

    @Override
    public String toString() {
        return "ApiDataItem{" +
                "id=" + id +
                ", dataSource='" + dataSource + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemNameCh='" + itemNameCh + '\'' +
                ", user=" + user +
                '}';
    }
}
