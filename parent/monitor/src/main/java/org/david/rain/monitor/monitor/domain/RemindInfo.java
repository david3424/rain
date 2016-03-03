package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-1-6.
 */
public class RemindInfo {
    private Integer id;
    private Integer itemId;
    private Integer userId;
    private String chName;
    private String phone;
    private String email;
    private Integer remindType;
    private String typeName;
    private String createTime;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
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

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RemindInfo{" +
                "status=" + status +
                ", createTime='" + createTime + '\'' +
                ", typeName='" + typeName + '\'' +
                ", remindType=" + remindType +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", chName='" + chName + '\'' +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", id=" + id +
                '}';
    }
}
