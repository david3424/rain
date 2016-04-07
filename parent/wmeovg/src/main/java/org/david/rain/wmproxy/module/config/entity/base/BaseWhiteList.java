package org.david.rain.wmproxy.module.config.entity.base;

import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.entity.Game;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.io.Serializable;
import java.util.Date;


public abstract class BaseWhiteList  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String appid;
	private Integer prizeid;
	private String prizename;
    private String title;//邮件标题
    private String text; //邮件正文
	private Integer count;
	private Integer sendCount;
	private Integer failCount;
	private Date createtime;
	private Byte status;
	
	//many-to-one
	private ClientInfo clientInfo;
	private Game game;
	private User user;
	
	public BaseWhiteList() {
		initialize();
	}

	public BaseWhiteList(Integer id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();

	public BaseWhiteList(Integer id, String appid, Integer prizeid, String prizename, String title,String text,
			Integer count,Integer sendCount, Integer failCount, Date createtime, Byte status, ClientInfo clientInfo, Game game,User user) {
		super();
		this.id = id;
		this.appid = appid;
		this.prizeid = prizeid;
		this.prizename = prizename;
        this.title=title;
        this.text=text;
		this.count = count;
		this.sendCount = sendCount;
		this.failCount = failCount;
		this.createtime = createtime;
		this.status = status;
		this.clientInfo = clientInfo;
		this.user = user;
		this.game = game;
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\nid=" + id);
		
		sb.append("\nappid=" + appid);
		sb.append("\nprizeid=" + prizeid);
		sb.append("\nprizename=" + prizename);
        sb.append("\ntitle=" + title);
        sb.append("\ntext=" + text);
		sb.append("\ncount=" + count);
		sb.append("\nclientInfo=" + clientInfo.getId());
		sb.append("\ngame=" + game.getId());
		
		return sb.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrizeid() {
		return prizeid;
	}

	public void setPrizeid(Integer prizeid) {
		this.prizeid = prizeid;
	}

	public String getPrizename() {
		return prizename;
	}

	public void setPrizename(String prizename) {
		this.prizename = prizename;
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

    public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppid() {
		return appid;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	
}
