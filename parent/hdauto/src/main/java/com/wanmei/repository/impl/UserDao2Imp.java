package com.david.web.wanmei.repository.impl;

import com.david.web.wanmei.entity.HdTable;
import com.david.web.wanmei.entity.User;
import com.david.web.wanmei.repository.Idao;
import com.david.web.wanmei.repository.UserDao;
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
@Qualifier("UserDao2Imp")
@Component
public class UserDao2Imp implements UserDao {
    private Idao idao;


    @Autowired
    public void setIdao(@Qualifier("daoImp")Idao idao) {
        this.idao = idao;
    }

    @Override
    public User findByLoginName(String loginName) throws Exception{

        String tableName = User.class.getAnnotation(HdTable.class).value();
        return (User) idao.queryObject(User.class, "select * from " + tableName + " where login_name = ?", loginName);
    }

    @Override
    public List<User> queryObjects(Class<User> userClass, String s) throws Exception{

        return idao.queryObjects(User.class, s);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User queryObject(Class<User> userClass, Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void insert(User user) throws SQLException {
        idao.insert(user);
    }

    @Override
    public List<Map<String, Object>> getList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Integer id) throws SQLException {
        idao.update("delete from ss_user where id = ?",id);
    }
}
