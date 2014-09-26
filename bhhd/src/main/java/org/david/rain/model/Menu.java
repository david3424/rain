package org.david.rain.model;

import java.io.Serializable;

public class Menu implements Serializable {

	private static final long serialVersionUID = 1976704147271906717L;
	private Integer permissionId;
	private Integer gameId;
	private String gameShortName;
	private String gameName;
	private Integer menuTypeId;
	private String menuTypeName;
	private String resourceUrl;
	private String resourceName;
	private String description;
	private Integer orderInt;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getGameShortName() {
		return gameShortName;
	}

	public void setGameShortName(String gameShortName) {
		this.gameShortName = gameShortName;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Integer getMenuTypeId() {
		return menuTypeId;
	}

	public void setMenuTypeId(Integer menuTypeId) {
		this.menuTypeId = menuTypeId;
	}

	public String getMenuTypeName() {
		return menuTypeName;
	}

	public void setMenuTypeName(String menuTypeName) {
		this.menuTypeName = menuTypeName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderInt() {
		return orderInt;
	}

	public void setOrderInt(Integer orderInt) {
		this.orderInt = orderInt;
	}

}
