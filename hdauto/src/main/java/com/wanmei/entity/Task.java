package com.wanmei.entity;


@HdTable("QRTZ_TRIGGERS")
public class Task extends IdEntity {

    private String display_name;
    private String trigger_name;
    private String trigger_group;
    private String table_name;
    private String datasource;
    private Integer role_id_type;
    private Integer send_type;
    private Integer consume_type;
    private Integer send_interface;
    private Integer send_check;
    private Long next_fire_time;
    private Long prev_fire_time;
    private Long start_time;
    private Long end_time;
    private Integer priority;
    private String trigger_type;
    private String trigger_state;
    private String statu;

    public String getTrigger_state() {
        return trigger_state;
    }

    public void setTrigger_state(String trigger_state) {
        this.trigger_state = trigger_state;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    /**
     * 处理参数类型转化
     *
     * @param trigger_name
     */
    public void setTrigger_name(String trigger_name) {
//        this.trigger_name = StringUtils.indexOf(trigger_name, "&") != -1?StringUtils.substringBefore(trigger_name, "&"):trigger_name;
        this.trigger_name = trigger_name;
    }

    public String getTrigger_type() {
        return trigger_type;
    }

    public void setTrigger_type(String trigger_type) {
        this.trigger_type = trigger_type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTrigger_group() {
        return trigger_group;
    }

    public void setTrigger_group(String trigger_group) {
        this.trigger_group = trigger_group;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Long getNext_fire_time() {
        return next_fire_time;
    }

    public void setNext_fire_time(Long next_fire_time) {
        this.next_fire_time = next_fire_time;
    }

    public Long getPrev_fire_time() {
        return prev_fire_time;
    }

    public void setPrev_fire_time(Long prev_fire_time) {
        this.prev_fire_time = prev_fire_time;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public Integer getRole_id_type() {
        return role_id_type;
    }

    public void setRole_id_type(Integer role_id_type) {
        this.role_id_type = role_id_type;
    }

    public Integer getSend_type() {
        return send_type;
    }

    public void setSend_type(Integer send_type) {
        this.send_type = send_type;
    }

    public Integer getSend_interface() {
        return send_interface;
    }

    public void setSend_interface(Integer send_interface) {
        this.send_interface = send_interface;
    }

    public Integer getSend_check() {
        return send_check;
    }

    public void setSend_check(Integer send_check) {
        this.send_check = send_check;
    }

    public Integer getConsume_type() {
        return consume_type;
    }

    public void setConsume_type(Integer consume_type) {
        this.consume_type = consume_type;
    }
}
