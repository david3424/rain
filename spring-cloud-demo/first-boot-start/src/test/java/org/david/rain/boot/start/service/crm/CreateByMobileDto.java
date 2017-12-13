package org.david.rain.boot.start.service.crm;


public class CreateByMobileDto  {

    private String operate_way;
    private String account_type;

    private String list_original_small;
    private String list_original_middle;
    private String list_original_big;

    private String fc;
    private String user_id;

    private String mobile;
    private String mobile_type;
    private String mobile_identify;

    private String cfp_id;
    private String introduce_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOperate_way() {
        return operate_way;
    }

    public void setOperate_way(String operate_way) {
        this.operate_way = operate_way;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getList_original_small() {
        return list_original_small;
    }

    public void setList_original_small(String list_original_small) {
        this.list_original_small = list_original_small;
    }

    public String getList_original_middle() {
        return list_original_middle;
    }

    public void setList_original_middle(String list_original_middle) {
        this.list_original_middle = list_original_middle;
    }

    public String getList_original_big() {
        return list_original_big;
    }

    public void setList_original_big(String list_original_big) {
        this.list_original_big = list_original_big;
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getCfp_id() {
        return cfp_id;
    }

    public void setCfp_id(String cfp_id) {
        this.cfp_id = cfp_id;
    }

    public String getIntroduce_id() {
        return introduce_id;
    }

    public void setIntroduce_id(String introduce_id) {
        this.introduce_id = introduce_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile_type() {
        return mobile_type;
    }

    public void setMobile_type(String mobile_type) {
        this.mobile_type = mobile_type;
    }

    public String getMobile_identify() {
        return mobile_identify;
    }

    public void setMobile_identify(String mobile_identify) {
        this.mobile_identify = mobile_identify;
    }
}
