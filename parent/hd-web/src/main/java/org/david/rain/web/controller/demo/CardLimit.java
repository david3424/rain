package org.david.rain.web.controller.demo;


import org.david.rain.common.entity.HdTable;

/**
 */
@HdTable(WebMallConst.TABLE_CODE_DAYLIMIT)
public class CardLimit {

    private Integer id;
    private Integer phase;
    private Integer restrictnum;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getRestrictnum() {
        return restrictnum;
    }

    public void setRestrictnum(Integer restrictnum) {
        this.restrictnum = restrictnum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
