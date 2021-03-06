package org.david.rain.dubbox.provider.dao.utils;
import java.io.Serializable;

public class EasyPageInfo implements Serializable {

    private static final long serialVersionUID = 587754556498974978L;

    private int rows = 1;//pageSize
    private int totalPage;
    private int totalResult;
    private int page;//pageNo
    private int currentResult;//startnum
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


    /**
     *
     * @param rows 每页数量
     * @param page 页码
     */
    public EasyPageInfo(int rows,int page) {
       this.rows = rows;
        this.page = page;
    }

    public EasyPageInfo() {
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