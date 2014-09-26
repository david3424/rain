package org.david.rain.model;

import java.io.Serializable;

public class RoleGameDetail implements Serializable {

	private static final long serialVersionUID = 6438025152770470028L;

	private Integer gameId;
	private String gameName;
	private String gameAb;
	private Integer roleId;

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

	public String getGameAb() {
		return gameAb;
	}

	public void setGameAb(String gameAb) {
		this.gameAb = gameAb;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
