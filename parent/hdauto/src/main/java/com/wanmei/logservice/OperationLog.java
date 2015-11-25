package com.david.web.pppppp.service.logservice;

import java.io.Serializable;

/**
 * User: Changel
 * Date: 14-2-14
 * Time: 下午2:33
 */

public class OperationLog implements Serializable {

    public static enum HandleType {
        LOGIN(1001),
        QUERY_CONSUMER_BY_NAME(2001),
        VALIDATE_ROLE_INFO(2002),
        SEND_PRIZE(3001),
        RESEND_PRIZE(3002),
        SEND_PRIZE_CALLBACK(3003);


        public final int value;

        private HandleType(int value) {
            this.value = value;
        }
    }

    private OperationLog() {

    }

    public static class LogBuilder {
        private OperationLog operationLog;

        private LogBuilder initOperationLog(Integer gameId, String hdname, Integer handle) {
            operationLog = new OperationLog();
            operationLog.gameid = gameId;
            operationLog.hdname = hdname;
            operationLog.handle = handle;

            return this;
        }

        public static LogBuilder init(Integer gameId, String hdname, Integer handle) {
            return new LogBuilder().initOperationLog(gameId, hdname, handle);
        }

        public LogBuilder setUsername(String username) {
            if (operationLog == null) {
                throw new IllegalStateException("请先init 一下");
            }
            operationLog.username = username;
            return this;
        }

        public LogBuilder setUserid(Long userid) {
            if (operationLog == null) {
                throw new IllegalStateException("请先init 一下");
            }
            operationLog.userid = userid;
            return this;
        }

        public LogBuilder setParams(String json) {
            if (operationLog == null) {
                throw new IllegalStateException("请先init 一下");
            }
            operationLog.params = json;
            return this;
        }

        public LogBuilder setResultDetail(String detail) {
            if (operationLog == null) {
                throw new IllegalStateException("请先init 一下");
            }
            operationLog.detail = detail;
            return this;
        }

        public LogBuilder setResult(int result) {
            if (operationLog == null) {
                throw new IllegalStateException("请先init 一下");
            }
            operationLog.result = result;
            return this;
        }

        public LogBuilder setCreatetime(String createtime) {
            if (operationLog == null) {
                throw new IllegalStateException("请先init 一下");
            }
            operationLog.createtime = createtime;
            return this;
        }

        public OperationLog build() {
            return operationLog;
        }

    }


    private Long id;
    private Integer gameid;
    private String hdname;
    private String username;
    private Long userid;
    private Integer handle;
    private String params;
    private Integer result;
    private String detail;
    private String createtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getHdname() {
        return hdname;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OperationLog{");
        sb.append("id=").append(id);
        sb.append(", gameid=").append(gameid);
        sb.append(", hdname='").append(hdname).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", userid=").append(userid);
        sb.append(", handle=").append(handle);
        sb.append(", params='").append(params).append('\'');
        sb.append(", result=").append(result);
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", createtime='").append(createtime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
