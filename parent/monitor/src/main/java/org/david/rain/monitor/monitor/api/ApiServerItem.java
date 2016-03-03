package org.david.rain.monitor.monitor.api;

/**
 * Created by czw on 2014/4/23.
 */
public class ApiServerItem {
    private Integer id;
    private String itemNameCh;

    private String changeTime;

    private ApiUser creator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemNameCh() {
        return itemNameCh;
    }

    public void setItemNameCh(String itemNameCh) {
        this.itemNameCh = itemNameCh;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public ApiUser getCreator() {
        return creator;
    }

    public void setCreator(ApiUser creator) {
        this.creator = creator;
    }
}
