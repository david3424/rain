package org.david.rain.dao.impl;

import org.david.rain.dao.CommonDao;
import org.david.rain.dao.MenuTypeDao;
import org.david.rain.model.MenuType;
import org.david.rain.model.MenuTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuTypeDaoImpl implements MenuTypeDao {

    private static final String TABLE_NAME_MENU_MODULE = "T_SYS_MENU_TYPE";
    private static final String TABLE_SYS_PERMISSION = "t_sys_permission";
    @Resource(name = "commonDao")
    private CommonDao commonDao;


    @Override
    public String addMenuType(MenuType menuType) {
        int rr = 0;
        String sql;
        Object[] obj;
        if (menuType.getMenuTypeId() != null && menuType.getMenuTypeId() > 0) {
            sql = "update  " + TABLE_NAME_MENU_MODULE + " set menu_type_name=?,menu_order=?,description=? where menu_type_id = ?";
            obj = new Object[]{menuType.getMenuTypeName(), menuType.getMenuOrder(), menuType.getDescription(), menuType.getMenuTypeId()};
        } else {
            sql = "insert into " + TABLE_NAME_MENU_MODULE + "(menu_type_name,menu_order,description) values (?,?,?) ";
            obj = new Object[]{menuType.getMenuTypeName(), menuType.getMenuOrder(), menuType.getDescription()};
        }
        rr = commonDao.addOrUpdate(sql, obj);
        return rr > 0 ? "success" : "error";
    }

    @Override
    public List<MenuType> getMenuTypeList(Integer displayStart, Integer displayLength) {
        StringBuilder sql = new StringBuilder();
        sql.append("select menu_type_id  ,menu_type_name ,menu_order,description  from " + TABLE_NAME_MENU_MODULE);
        sql.append(" limit ?,? ");
        Object[] args = new Object[]{displayStart, displayLength};
        return commonDao.query(sql.toString(), args, new MenuTypeMapper());
    }

    @Override
    public int getMenuTypeListCount() {
        String sql = "select count(*) from " + TABLE_NAME_MENU_MODULE;
        Object[] args = new Object[]{};
        return commonDao.queryForInt(sql, args);
    }

    @Override
    public int getMenuTypeCountUsed(Integer menuTypeId) {
        return commonDao.queryForInt(" select count(1) from " + TABLE_SYS_PERMISSION + " where menu_type_id = ? ", new Object[]{menuTypeId});
    }

    @Override
    public String deleteMenuTypeById(Integer menuTypeId) {
        return commonDao.addOrUpdate("delete from " + TABLE_NAME_MENU_MODULE + " where menu_type_id = ? ", new Object[]{menuTypeId}) > 0 ? "success" : "error";
    }

}
