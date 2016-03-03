package org.david.rain.monitor.monitor.service;

import org.david.rain.monitor.monitor.domain.Role;
import org.david.rain.monitor.monitor.domain.User;
import org.david.rain.monitor.monitor.persistence.RoleMapper;
import org.david.rain.monitor.monitor.persistence.UserMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by czw on 14-1-3.
 */

@Service
public class UserService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    public void saveUser(User user) {
        user.setStatus(0);
        user.setCreateTime(DateUtils.getCurrentFormatDateTime());
        userMapper.saveUser(user);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    public List<User> getUserPageList(EasyPageInfo pageInfo) {
        return userMapper.getAllUserListPage(pageInfo);
    }


    @Cacheable(value = "cache3min", key = "'get_user_list'")
    public List<User> getUserList() {
        return userMapper.getAllUserList();
    }

    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    public User selectUserRoles(Integer id){
        return userMapper.selectUserRoles(id);
    }


    public List<Role> getAllRoles() {

        return roleMapper.getAllRoles();
    }

    public Role getRoleById(int id) {
        return roleMapper.getRoleById(id);
    }

    public void saveUserRoles(Integer userid, String roleid) {
        userMapper.saveUserRoles(userid,roleid);
    }

    public void deleteUserRoles(Integer id) {

        userMapper.deleteUserRoles(id);
    }
}
