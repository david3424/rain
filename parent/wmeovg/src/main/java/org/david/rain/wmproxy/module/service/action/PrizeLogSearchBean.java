package org.david.rain.wmproxy.module.service.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.sf.json.JSONObject;

/**
 * @ClassName PrizeLogSearchBean
 * @Description 查询条件Bean
 * @version 1.0
 * @date 2010-8-17 上午10:28:31
 */
public class PrizeLogSearchBean {

	protected static Logger log = LoggerFactory.getLogger(PrizeLogSearchBean.class);
	
	private String gid;
	private String cid;
	private String appid;
	private Integer prizeid;
	private String startdate;
	private String starttime;
	private String enddate;
	private String endtime;
	private String account;

	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		if(startdate != null && startdate.trim().length() >0 )
			this.startdate = startdate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		if(starttime != null && starttime.trim().length() >0 )
			this.starttime = starttime;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		if(enddate != null && enddate.trim().length() >0 )
			this.enddate = enddate;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		if(endtime != null && endtime.trim().length() >0 )
			this.endtime = endtime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		if(account != null && account.trim().length() >0 )
			this.account = account;
	}
	public void setCid(String cid) {
		if(cid != null && cid.trim().length() >0 )
			this.cid = cid;
	}
	public String getCid() {
		return cid;
	}
	public void setGid(String gid) {
		if(gid != null && gid.trim().length() >0 )
			this.gid = gid;
	}
	public String getGid() {
		return gid;
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public Integer getPrizeid() {
		return prizeid;
	}
	public void setPrizeid(Integer prizeid) {
		this.prizeid = prizeid;
	}
	public String toString(){
		
		JSONObject jo = new JSONObject();
		
		jo.put("流水号", this.getGid());
		jo.put("应用", this.getAppid());
		jo.put("物品ID", this.getPrizeid());
		jo.put("账号", this.getAccount());
		jo.put("客户端标识", this.getCid());
		if(this.getEnddate() != null && this.getEndtime() != null)
			jo.put("起始时间", this.getEnddate() + " " + this.getEndtime());
		else
			jo.put("起始时间", this.getEnddate());
		
		if(this.getStartdate() != null && this.getStarttime() != null)
			jo.put("截止时间", this.getStartdate() + " " + this.getStarttime());
		else
			jo.put("截止时间", this.getStartdate());
		
		return jo.toString();
	}
	
	public static Finder getFinder(PrizeLogSearchBean searchBean, String sort, String dir){
		String hql = "select bean from PrizeLog bean";
		
		Finder finder = Finder.create(hql);
		finder.append(" where 1=1 ");
		
		if (searchBean != null) {
			
			String gid = SqlUtils.escape(searchBean.getGid());
			if (gid != null && gid.trim().length() > 0)
				finder.append(" and bean.gid = :gid").setParam("gid", gid.trim());
			
			String account = SqlUtils.escape(searchBean.getAccount());
			String cid = searchBean.getCid();
			String appid = searchBean.getAppid();
			Integer prizeid = searchBean.getPrizeid();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date starttime = null;
			try {
				if (searchBean.getStartdate() != null && searchBean.getStartdate().trim().length() > 0) {
					if (searchBean.getStarttime() != null && searchBean.getStarttime().trim().length() > 0)
						starttime =sdf1.parse(searchBean.getStartdate().trim()+ " " + searchBean.getStarttime().trim());
					else
						starttime = sdf2.parse(searchBean.getStartdate().trim());
				}
			} catch (Exception e) {
				
				log.error("兑换记录查询：起始时间错误", e);
			}
			Date endtime = null;
			try {
				if (searchBean.getEnddate() != null && searchBean.getEnddate().trim().length() > 0) {
					if (searchBean.getEndtime() != null && searchBean.getEndtime().trim().length() > 0)
						endtime = sdf1.parse(searchBean.getEnddate().trim() + " " + searchBean.getEndtime().trim());
					else
						endtime = sdf2.parse(searchBean.getEnddate().trim());
				}
			} catch (Exception e) {

				log.error("兑换记录查询：截止时间错误", e);
			}

			if (account != null && account.trim().length() > 0) {
				finder.append(" and bean.account = :account").setParam(
						"account", account.trim());
			}
			if (cid != null && cid.trim().length() > 0) {
				finder.append(" and bean.cid = :cid")
						.setParam("cid", cid);
			}
			if (appid != null && appid.trim().length() > 0) {
				finder.append(" and bean.appid = :appid")
						.setParam("appid", appid);
			}
			if (prizeid != null && prizeid > 0) {
				finder.append(" and bean.prizeid = :prizeid")
						.setParam("prizeid", prizeid);
			}
			if (starttime != null) {
				finder.append(" and bean.requestTime >= :starttime").setParam(
						"starttime", starttime);
				//System.out.println("=======" + starttime);
			}
			if (endtime != null) {
				finder.append(" and bean.requestTime <= :endtime").setParam(
						"endtime", endtime);
				//System.out.println("=======" + endtime);
			}
		}

		if (sort != null && dir != null)
			finder.append(" order by bean" + "." + sort + " " + dir);
		
		return finder;
	}
}
