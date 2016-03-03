package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 */
public interface RoleMapper {

    @Select("select id,name,permissions from ss_role")
    public List<Role> getAllRoles();

    @Select("select id,name,permissions from ss_role where id = #{id} ")
    public Role getRoleById(int id);

    @Insert("insert into ss_role(name,permissions) values #{name},#{permissions} ")
    public void saveRole(Role role);

    @Update("update ss_role set name = #{name} ,permissions=#{permissions} where id = #{id}")
    public int updateRole(Role role);


    @Update("delete from ss_role  where id = #{id}")
    public int deleteRole(Long id);

    @Select("select id,name,permissions from ss_role where name = #{name}")
    public Role getRoleByName(String name);
    /*在xml中写sql*/
    public Role selectRoleUsers(Long id);

    @Update("delete from ss_user_role where role_id = #{id}")
    public void deleteRoleUsers(Long id);
}
