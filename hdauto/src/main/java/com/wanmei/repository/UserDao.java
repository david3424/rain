package com.wanmei.repository;


import com.wanmei.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDao {
	User findByLoginName(String loginName) throws Exception;

    List<User> queryObjects(Class<User> userClass, String s) throws Exception;

    User queryObject(Class<User> userClass, Integer id);

    void insert(User user) throws SQLException;
     public List<Map<String,Object>> getList() ;

    void delete(Integer id) throws SQLException;
}
