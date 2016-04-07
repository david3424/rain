package org.david.rain.wmproxy.module.upload.entity;

import java.util.Set;

public class ResultBean {

	private Set<UploadPrizeBean> beans;
	private Integer appid;
	private Integer zeroCount;
	private Integer xmlTotalCount;
	private Integer xmlRepeatCount;
	
	public Set<UploadPrizeBean> getBeans() {
		return beans;
	}
	public void setBeans(Set<UploadPrizeBean> beans) {
		this.beans = beans;
	}
	public Integer getAppid() {
		return appid;
	}
	public void setAppid(Integer appid) {
		this.appid = appid;
	}
	
	public Integer getZeroCount() {
		return zeroCount;
	}
	public void setZeroCount(Integer zeroCount) {
		this.zeroCount = zeroCount;
	}
	public Integer getXmlTotalCount() {
		return xmlTotalCount;
	}
	public void setXmlTotalCount(Integer xmlTotalCount) {
		this.xmlTotalCount = xmlTotalCount;
	}
	public Integer getXmlRepeatCount() {
		return xmlRepeatCount;
	}
	public void setXmlRepeatCount(Integer xmlRepeatCount) {
		this.xmlRepeatCount = xmlRepeatCount;
	}
}
