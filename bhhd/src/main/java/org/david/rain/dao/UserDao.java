package org.david.rain.dao;


import org.david.rain.model.User;

import java.util.List;

public interface UserDao {

	List<User> getSecurityUserByAccount(String account);

	List<String> getSecurityRolesByAccount(String account);

	int countUserByConditions(Integer roleId, Integer userStatus,
                              String userName, String account);

	List<User> getUserGrid(Integer roleId, Integer userStatus, String userName,
                           String account, int from, int length, String sidx, String sord);

	int countByAccount(String account);

	int insert(String userName, String account, String userPwd, Integer roleId,
               Integer userStatus, String agent);

	int countByAccountWithoutId(Integer userId, String account);

	int updateWithoutPwd(Integer userId, String userName, String account,
                         Integer roleId, Integer userStatus, String agent);

	int update(Integer userId, String userName, String account, String userPwd,
               Integer roleId, Integer userStatus, String agent);

	int delete(Integer userId);

	List<User> getUserById(Integer userId);

	int countByPassword(String account, String password);

	int updatePassword(String account, String password);

}
