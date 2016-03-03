package org.david.rain.monitor.monitor.util;
import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = 587754556498974978L;

    private int showCount = 10;
    private int totalPage;
    private int totalResult;
    private int currentPage;
    private int currentResult;

    private String sortField;
    private String order;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "showCount=" + showCount +
                ", totalPage=" + totalPage +
                ", totalResult=" + totalResult +
                ", currentPage=" + currentPage +
                ", currentResult=" + currentResult +
                ", sortField='" + sortField + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}