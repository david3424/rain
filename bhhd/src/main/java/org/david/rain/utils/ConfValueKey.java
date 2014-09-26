package org.david.rain.utils;

public enum ConfValueKey {
	
	GAME_SET_ACTIVITY_URI		("开启活动的URI",		"/systems/activity_open",					"edit"),
	GAME_DEL_ACTIVITY_URI		("删除活动的URI",		"/systems/activity_delete",					"edit"),
	GAME_SET_ACTIVITY_STATE_URI	("关闭活动的URI",		"/systems/activity_close",			"edit"),
	;
	ConfValueKey(String title, String defaultValue, String status) {
		this.title = title;
		this.status = status;
		this.defaultValue = defaultValue;
	}
	
	private String title;
	private String status;		//是否在后台界面中显示 允许手动修改
	private String defaultValue;	//默认值
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
