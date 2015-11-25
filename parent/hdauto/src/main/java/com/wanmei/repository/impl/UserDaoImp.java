package com.david.web.pppppp.repository.impl;

import com.david.web.pppppp.entity.HdTable;
import com.david.web.pppppp.entity.User;
import com.david.web.pppppp.repository.DbUtilsTemplate;
import com.david.web.pppppp.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gameuser
 * Date: 12-12-13
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
@Qualifier("UserDaoImp")
@Component
public class UserDaoImp implements UserDao {
    private DbUtilsTemplate dbUtilsTemplate;


    @Autowired
    public void setDbUtilsTemplate(DbUtilsTemplate dbUtilsTemplate) {
        this.dbUtilsTemplate = dbUtilsTemplate;
    }

    @Override
    public User findByLoginName(String loginName) {
        String tableName = User.class.getAnnotation(HdTable.class).value();
        return (User) dbUtilsTemplate.findBy( "select * from " + tableName + " where login_name = ?", loginName);
    }

    @Override
    public List<User> queryObjects(Class<User> userClass, String s) {

        return dbUtilsTemplate.find(User.class,s);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User queryObject(Class<User> userClass, Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void insert(User user) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Map<String,Object>> getList() {
     return dbUtilsTemplate.find("select * from ss_user");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        dbUtilsTemplate.update("delete from ss_user where id = ?", id);
    }
}
