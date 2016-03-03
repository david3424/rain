package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-2-24.
 */
public class DataAttrSetting {
    private Integer itemId;
    private String attrName;
    private String chName;
    private String memo;
    private String sql;
    private String dataSource;
    private Integer status;

    public static final int USE = 1;
    public static final int NO_USE = 0;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataAttrSetting{" +
                "itemId=" + itemId +
                ", attrName='" + attrName + '\'' +
                ", chName='" + chName + '\'' +
                ", memo='" + memo + '\'' +
                ", sql='" + sql + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", status=" + status +
                '}';
    }
}
