package org.david.rain.act.utils;
import java.io.Serializable;

public class EasyPageInfo implements Serializable {

    private static final long serialVersionUID = 587754556498974978L;

    private int rows = 1;
    private int totalPage;
    private int totalResult;
    private int page;
    private int currentResult;
    private String sort;
    private String order;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCurrentResult() {
        return (page-1)*rows;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "EasyPageInfo{" +
                "rows=" + rows +
                ", totalPage=" + totalPage +
                ", totalResult=" + totalResult +
                ", page=" + page +
                ", currentResult=" + currentResult +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}