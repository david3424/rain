package org.david.java.thread;

import java.util.Date;
import java.util.Map;

/**
 * Created by philo on 2015/11/11.
 */
public class Message {

    /***
     * 定义Message Map 中可能的key的名字
     * **/

    /***
     * 电话号码，用于短信扣
     * */
    public static final String MOBILE = "mobile";

    /***
     * 发送内容
     * **/
    public static final String CONTENT = "content";

    /**
     * 邮件
     * **/
    public static final String EMAIL = "email";

    // 定义列名称
    public static final String COL_ID = "id";
    public static final String COL_MSG_ID = "msg_id";
    public static final String COL_CONTETN = "content";
    public static final String COL_SOURCE = "source";
    public static final String COL_STATUS = "status";
    public static final String COL_REAL_SEND_TIME = "real_send_time";
    public static final String COL_CREATE_TIME = "create_time";
    public static final String COL_SEND_TIME = "send_time";
    public static final String COL_SEND_TIME_MILLIS = "send_time_millis";
    public static final String COL_MESSAGE = "message";
    public static final String COL_SEND_TYPE = "send_type";
    public static final String COL_FAIL_COUNT = "fail_count";
    public static final String COL_MODIFY_TIME = "modify_time";

    public Message(Long id, Integer status) {
        this.id = id;
        this.status = status;
    }

    /***
     * 消息id自增ID
     * **/
    private Long id;

    /**
     * 消息的ID 可以采用uuid
     * **/
    private String msgId;

    /**
     * 消息说明
     * **/
    private String content;

    /***
     * 消息对象在数据库中保存json串儿
     * **/
    private Map message;

    /**
     * map转换成json用于保存在数据库
     * **/
    private String messageJson;

    /***
     * 消息来源
     * **/
    private String source;

    /**
     * 发送类型 1-sms 2-email 3-app
     * **/
    private Integer sendType;

    /***
     * 发送状态 0-未发送 1-发送成功 2-取消发送 3-超过重试次数不再发送
     * **/
    private Integer status;

    /***
     * 当前时间的毫秒数，用于判断是否马上发送,初始化为当前时间
     * **/
    private Long sendTimeMillis = System.currentTimeMillis();

    /***
     * 发送时间用与保存消息的发送时间，是sendm
     * */
    private Date sendTime;

    /**
     * 创建时间
     * **/
    private Date createTime;

    /***
     * 真实发送时间
     * **/
    private Date realSendTime;

    /***
     * 最后一次修改时间
     * **/
    private Date modifyTime;

    /***
     * 失败次数，超过次数后不再重新发送
     * **/
    private Integer failCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map getMessage() {
        return message;
    }

    public void setMessage(Map message) {
        this.message = message;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Long getSendTimeMillis() {
        return sendTimeMillis;
    }

    public void setSendTimeMillis(Long sendTimeMillis) {
        this.sendTimeMillis = sendTimeMillis;
    }

    public String getMessageJson() {
        return messageJson;
    }

    public void setMessageJson(String messageJson) {
        this.messageJson = messageJson;
    }

    public Date getRealSendTime() {
        return realSendTime;
    }

    public void setRealSendTime(Date realSendTime) {
        this.realSendTime = realSendTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                ", message=" + message +
                ", messageJson='" + messageJson + '\'' +
                ", source='" + source + '\'' +
                ", sendType='" + sendType + '\'' +
                ", status=" + status +
                ", sendTimeMillis=" + sendTimeMillis +
                ", sendTime=" + sendTime +
                ", createTime=" + createTime +
                ", realSendTime=" + realSendTime +
                '}';
    }
}
