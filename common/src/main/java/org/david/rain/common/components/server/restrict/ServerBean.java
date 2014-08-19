package org.david.rain.common.components.server.restrict;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 13-6-4
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class ServerBean implements Serializable {
    private Integer id;
    private String eventName;
    private Integer server;
    private String serverName;
    private String eventStart;
    private String eventEnd;
    private String searchStart;
    private String searchEnd;
    private String oneStart;
    private String oneEnd;
    private String twoStart;
    private String twoEnd;
    private String threeStart;
    private String threeEnd;
    private String createTime;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getSearchStart() {
        return searchStart;
    }

    public void setSearchStart(String searchStart) {
        this.searchStart = searchStart;
    }

    public String getSearchEnd() {
        return searchEnd;
    }

    public void setSearchEnd(String searchEnd) {
        this.searchEnd = searchEnd;
    }

    public String getOneStart() {
        return oneStart;
    }

    public void setOneStart(String oneStart) {
        this.oneStart = oneStart;
    }

    public String getOneEnd() {
        return oneEnd;
    }

    public void setOneEnd(String oneEnd) {
        this.oneEnd = oneEnd;
    }

    public String getTwoStart() {
        return twoStart;
    }

    public void setTwoStart(String twoStart) {
        this.twoStart = twoStart;
    }

    public String getTwoEnd() {
        return twoEnd;
    }

    public void setTwoEnd(String twoEnd) {
        this.twoEnd = twoEnd;
    }

    public String getThreeStart() {
        return threeStart;
    }

    public void setThreeStart(String threeStart) {
        this.threeStart = threeStart;
    }

    public String getThreeEnd() {
        return threeEnd;
    }

    public void setThreeEnd(String threeEnd) {
        this.threeEnd = threeEnd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
