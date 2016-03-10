package org.david.rain.monitor.monitor.util;

import java.io.Serializable;
import java.util.List;

/*easyui分页识别的参数 page、records、rows*/
public class PaginationJsonObject<T> implements Serializable {
    /**
     * Current page pageNo
     */
    private int page;

    /**
     * Total pages
     */
    private int total;

    /**
     * Total number of records total
     */
    private int records;

    /**
     * Contains the actual data
     */
    private List<T> rows;

    public PaginationJsonObject(List<T> rows, EasyPageInfo page) {
        this.rows = rows;
        this.total = page.getTotalResult();
        this.records = page.getTotalResult();
        this.page = page.getPage();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}