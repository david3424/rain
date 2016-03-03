package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 13-12-9.
 */
public class ServerCurrentValue {
    private Integer id;
    private Integer itemId;
    private Integer windowLength;
    private Integer abnTimes;
    private Integer abnLevel;
    private String lastChange;
    private String thisWindowBegin;
    private String thisWindowEnd;
    private Integer status;

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

    public Integer getWindowLength() {
        return windowLength;
    }

    public void setWindowLength(Integer windowLength) {
        this.windowLength = windowLength;
    }

    public Integer getAbnTimes() {
        return abnTimes;
    }

    public void setAbnTimes(Integer abnTimes) {
        this.abnTimes = abnTimes;
    }

    public Integer getAbnLevel() {
        return abnLevel;
    }

    public void setAbnLevel(Integer abnLevel) {
        this.abnLevel = abnLevel;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public String getThisWindowBegin() {
        return thisWindowBegin;
    }

    public void setThisWindowBegin(String thisWindowBegin) {
        this.thisWindowBegin = thisWindowBegin;
    }

    public String getThisWindowEnd() {
        return thisWindowEnd;
    }

    public void setThisWindowEnd(String thisWindowEnd) {
        this.thisWindowEnd = thisWindowEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
