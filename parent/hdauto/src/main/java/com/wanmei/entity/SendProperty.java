package com.david.web.wanmei.entity;

import com.david.web.wanmei.common.DateUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-6-17
 * Time: 下午8:07
 * To change this template use File | Settings | File Templates.
 */

@HdTable("event_send_prize_properties")
public class SendProperty implements Serializable{
    public static enum SendInterface {

        MAIN_LAND(1),
        ABROAD_HK(2);


        public final int value;

        private SendInterface(int value) {
            this.value = value;
        }
    }

    public static enum ConsumerType {
        COUPON(1),
        GOLD(2);
        public final int value;

        private ConsumerType(int value) {
            this.value = value;
        }
    }

    public static enum SendType {
        PRIZE_ID(1),
        COUPON(2),
        COUPON_MULTI(3);

        public final int value;

        private SendType(int value) {
            this.value = value;
        }
    }


    public static enum Status {
        CLOSE(0), OPEN(1);
        public final int value;

        private Status(int value) {
            this.value = value;
        }
    }

    private String table_name;
    private String datasource;
    private Integer role_id_type;
    private Integer send_type;
    private Integer consume_type;
    private Integer send_interface;
    private Integer send_check;
    private String create_time;
    private Integer status;


    public SendProperty() {

    }

    public SendProperty(Task task) {
        this.table_name = task.getTable_name();
        this.datasource = task.getDatasource();
        this.role_id_type = task.getRole_id_type();
        this.send_type = task.getSend_type();
        this.send_check = task.getSend_check();
        this.consume_type = task.getConsume_type();
        this.send_interface = task.getSend_interface();
        this.create_time = DateUtils.getCurrentFormatDateTime();
        this.status = 1;//一添加就开启（用于总开关）
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSend_type() {
        return send_type;
    }

    public void setSend_type(Integer send_type) {
        this.send_type = send_type;
    }

    public Integer getConsume_type() {
        return consume_type;
    }

    public void setConsume_type(Integer consume_type) {
        this.consume_type = consume_type;
    }
}
