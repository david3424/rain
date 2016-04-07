package org.david.rain.wmproxy.module.upload.entity;

public class UploadPrizeBean implements Comparable<UploadPrizeBean>{

	private String appid;
	private String prizename;
	private Integer prizeid;
	private Integer count;
    private String title;//邮件标题
    private String text; //邮件正文
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPrizename() {
		return prizename;
	}
	public void setPrizename(String prizename) {
		this.prizename = prizename;
	}
	public Integer getPrizeid() {
		return prizeid;
	}
	public void setPrizeid(Integer prizeid) {
		this.prizeid = prizeid;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
	public String toString() {
		return "UploadPrizeBean [appid=" + appid + ", prizeid=" + prizeid
				+ ", prizename=" + prizename + ", count=" + count + ", title=" + title +", text=" + text +"]";
	}
	@Override
	public int compareTo(UploadPrizeBean o) {
		
		if((this.appid!=null && o.appid != null && this.appid.trim().equalsIgnoreCase(o.appid.trim()))||
				(this.appid==null && o.appid == null)){
			
			if((this.prizeid!=null && o.prizeid != null && this.prizeid == o.prizeid)||
					(this.prizeid==null && o.prizeid == null)){
				
				return 0;
			}
			return 1;
		}
		
		return -1;
	}
	
	
}
