package org.david.rain.web.service.hdinterface.entity;

import org.apache.commons.lang.StringUtils;
import org.david.rain.common.entity.HdTable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
@HdTable("hd_webmall_interface_user")
public class UserBean implements Serializable {
    private Integer id;
    private String username;
    private String truename;
    private String phone;

    private String province;
    private String city;
    private String country;
    private String address;
    private String email;
    private Integer usedscore;
    private Integer totalscore;
    private String updatetime;
    private String createtime;
    private String ip;
    private Integer status;


    public Integer getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(Integer totalscore) {
        this.totalscore = totalscore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getTruename() {
        //truename  = StringEscapeUtils.escapeHtml4(truename);
        //truename = html(truename);
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        //address  = StringEscapeUtils.escapeHtml4(address);
        //address = html(address);
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUsedscore() {
        return usedscore;
    }

    public void setUsedscore(Integer usedscore) {
        this.usedscore = usedscore;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    //信息提交的时候 去除事件
        private   String html(String content) {
            if(content==null) return "";
            String html = content;
            html = StringUtils.replace(html, "'", "&apos;");
            html = StringUtils.replace(html, "\"", "&quot;");
            html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
            //html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
            html = StringUtils.replace(html, "<", "&lt;");
            html = StringUtils.replace(html, ">", "&gt;");
            return html;
        }
}
