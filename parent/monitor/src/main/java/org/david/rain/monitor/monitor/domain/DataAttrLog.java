package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-3-3.
 */
public class DataAttrLog {
    private Integer id;
    private Integer itemId;
    private String attrName;
    private String chName;
    private Long itemTurns;
    private Long result;
    private Integer status;

    public DataAttrLog() {

    }

    public DataAttrLog(DataAttrSetting setting, Long itemTurns,Long result) {
        this.itemId = setting.getItemId();
        this.attrName = setting.getAttrName();
        this.chName = setting.getChName();
        this.itemTurns = itemTurns;
        this.result = result;
        this.status = 0;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public Long getItemTurns() {
        return itemTurns;
    }

    public void setItemTurns(Long itemTurns) {
        this.itemTurns = itemTurns;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataAttrLog{" +
                "status=" + status +
                ", result=" + result +
                ", itemTurns=" + itemTurns +
                ", chName='" + chName + '\'' +
                ", attrName='" + attrName + '\'' +
                ", itemId=" + itemId +
                ", id=" + id +
                '}';
    }
}
