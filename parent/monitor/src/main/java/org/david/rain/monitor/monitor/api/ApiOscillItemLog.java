package org.david.rain.monitor.monitor.api;


/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-5-7
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 * 返回手机端  波动异常数据bean
 */
public class ApiOscillItemLog {
    private Long id;
    private Integer itemId;
    private String attrName;
    private Integer times;
    private Long total;
    private ApiOscillDateItem dataItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public ApiOscillDateItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(ApiOscillDateItem dataItem) {
        this.dataItem = dataItem;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
