package org.david.rain.model;

import java.io.Serializable;

public class GameBean implements Serializable{

	private static final long serialVersionUID = 68942376587384143L;
	private Integer gameId;
	private String gameName;
	private String shortName;
	//新加分端
	private Integer client;
	
	
	public GameBean() {
		super();
	}

	public GameBean(Integer gameId, String gameName, String shortName) {
		super();
		this.gameId = gameId;
		this.gameName = gameName;
		this.shortName = shortName;
	}
	
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getClient() {
		return client;
	}
	public void setClient(Integer client) {
		this.client = client;
	}
	

}
