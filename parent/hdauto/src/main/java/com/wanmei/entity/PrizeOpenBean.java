package com.david.web.pppppp.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

@HdTable("hd_sendprize_open")
public class PrizeOpenBean extends IdEntity {

    private String hdname;
    private String tbname;
    private String prizelist;
    private Integer open;


    public String getHdname() {
        return hdname;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public String getTbname() {
        return tbname;
    }

    public void setTbname(String tbname) {
        this.tbname = tbname;
    }

    public String getPrizelist() {
        return prizelist;
    }

    public void setPrizelist(String prizelist) {
        this.prizelist = prizelist;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}