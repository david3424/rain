package org.david.rain.monitor.monitor.service;

import org.david.rain.monitor.monitor.domain.Role;
import org.david.rain.monitor.monitor.persistence.RoleMapper;
import org.david.rain.monitor.monitor.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */

@Service
public class RoleService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    public void saveRole(Role role) {
        roleMapper.saveRole(role);
    }

    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    public int deleteRole(Long id) {
        return roleMapper.deleteRole(id);
    }




    @Cacheable(value = "cache3min", key = "'get_role_list'")
    public List<Role> getRoleList() {
        return roleMapper.getAllRoles();
    }

    public Role getRoleByName(String name) {
        return roleMapper.getRoleByName(name);
    }

    public Role selectRoleUsers(Long id){
        return roleMapper.selectRoleUsers(id);
    }


    public List<Role> getAllRoles() {

        return roleMapper.getAllRoles();
    }

    public Role getRoleById(int id) {
        return roleMapper.getRoleById(id);
    }


    public void deleteRoleUsers(Long id) {

        roleMapper.deleteRoleUsers(id);
    }
}
