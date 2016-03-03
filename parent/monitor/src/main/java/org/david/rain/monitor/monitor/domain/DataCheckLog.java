package org.david.rain.monitor.monitor.domain;

import org.david.rain.monitor.monitor.util.DateUtils;

/**
 * Created by czw on 14-3-3.
 */
public class DataCheckLog {
    public static enum Status {
        NORMAL(1),    //规则满足，正常状态。
        EXCEPTION(2),//规则不满足，错误状态
        SYS_ERROR(3);//这里一般是由于程序报错了，比如某个属性值查询sql写错了

        public final int value;

        private Status(int value) {
            this.value = value;
        }
    }


    public DataCheckLog() {

    }


    public DataCheckLog(DataCheckSetting checkSetting,Long turns) {
        this.itemId = checkSetting.getItemId();
        this.checkerName = checkSetting.getCheckerName();
        this.createTime = DateUtils.getCurrentFormatDateTime();
        this.status = checkSetting.getStatus();
        this.itemTurns = turns;
    }


    private Integer id;
    private Integer itemId;
    private String checkerName;
    private Long itemTurns;
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

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Long getItemTurns() {
        return itemTurns;
    }

    public void setItemTurns(Long itemTurns) {
        this.itemTurns = itemTurns;
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

    @Override
    public String toString() {
        return "DataCheckLog{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", checkerName='" + checkerName + '\'' +
                ", itemTurns=" + itemTurns +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
