package org.david.rain.dao;

import org.david.rain.model.MenuType;

import java.util.List;

/**
 * Created by david on 2014/10/30.
 *
 */
public interface MenuTypeDao {


    public String addMenuType(MenuType menuType);


    List<MenuType> getMenuTypeList(Integer displayStart, Integer displayLength);

    int getMenuTypeListCount();

    int getMenuTypeCountUsed(Integer menuTypeId);

    String deleteMenuTypeById(Integer menuTypeId);
}
