package org.david.rain.web.controller.lottery;

import org.david.rain.common.entity.HdTable;

import org.david.rain.common.entity.IdEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-10
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */

@HdTable("lottery_demo_lotterytimes")
public class LotteryTimes extends IdEntity {

    private String username;
    private Integer signtimes;
    private Integer consumetimes;
    private String signdate;
    private Integer status;

    public LotteryTimes() {

    }

    public LotteryTimes(String username, Integer signtimes,
                        String signdate) {
        this.username = username;
        this.signtimes = signtimes;
        this.consumetimes = 0;
        this.signdate = signdate;
        this.status = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSigntimes() {
        return signtimes;
    }

    public void setSigntimes(Integer signtimes) {
        this.signtimes = signtimes;
    }

    public Integer getConsumetimes() {
        return consumetimes;
    }

    public void setConsumetimes(Integer consumetimes) {
        this.consumetimes = consumetimes;
    }

    public String getSigndate() {
        return signdate;
    }

    public void setSigndate(String signdate) {
        this.signdate = signdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
