package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-3-24.
 * 这个主要是为手机提供接口用的bean,相比OscillationLog 多了item info
 */

public class OscillationItemLog extends OscillationLog {
    protected DataItem dataItem;

    public DataItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    public OscillationItemLog() {

    }

    public OscillationItemLog(OscillationLog log, DataItem item) {
        this.setStatus(log.getStatus());
        this.setItemTurns(log.getItemTurns());
        this.setCurrentNum(log.getCurrentNum());
        this.setCreateTime(log.getCreateTime());
        this.setDataItem(item);
        this.setAttrName(log.getAttrName());
        this.setPhaseDetail(log.getPhaseDetail());
        this.setPhaseStrategyId(log.getPhaseStrategyId());
        this.setItemId(log.getItemId());
        this.setId(log.getId());
        this.setTimes(log.getTimes());
        this.setTotal(log.getTotal());
    }
}
