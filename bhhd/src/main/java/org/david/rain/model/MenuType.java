package org.david.rain.model;

import java.io.Serializable;

public class MenuType implements Serializable {

	private static final long serialVersionUID = -8036009672297754689L;
	private Integer menuTypeId;
	private String menuTypeName;
	private Integer menuOrder;
	private String description;

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

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public MenuType(String menuTypeName, Integer menuOrder, String description) {
        this.menuTypeName = menuTypeName;
        this.menuOrder = menuOrder;
        this.description = description;
    }

    public MenuType() {
    }

    @Override
    public String toString() {
        return "MenuType{" +
                "menuTypeId=" + menuTypeId +
                ", menuTypeName='" + menuTypeName + '\'' +
                ", menuOrder=" + menuOrder +
                ", description='" + description + '\'' +
                '}';
    }
}
