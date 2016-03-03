package org.david.rain.monitor.monitor.controller.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-3
 * Time: 下午2:50
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    private Integer id;
    private String text;
    private String url;
    private Integer parentId;//父接口id
    private Integer isLeaf;
    private Integer status;

    private List<Node> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Integer leaf) {
        isLeaf = leaf;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", parentId='" + parentId + '\'' +
                ", isLeaf=" + isLeaf +
                ", status=" + status +
                ", children=" + children +
                '}';
    }
}
