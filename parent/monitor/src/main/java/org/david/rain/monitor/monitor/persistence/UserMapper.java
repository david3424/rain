package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.controller.model.Node;
import org.david.rain.monitor.monitor.domain.User;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by czw on 14-1-3.
 */
public interface UserMapper {

    @Insert("insert into monitor_user(username,ch_name,email,phone,create_time,status)" +
            " values(#{username},#{chName},#{email},#{phone},#{createTime},#{status})")
    public void saveUser(User user);

    @Update("update monitor_user set ch_name = #{chName}, email = #{email}, phone = #{phone},roles=#{roles}" +
            " where id = #{id}")
    public int updateUser(User user);

//    @Delete("delete from monitor_user where id = #{id}")
    public int deleteUser(Integer[] ids);

//    @Select("select id,username,ch_name as chName,email,phone,create_time as createTime,status,roles from monitor_user")
    public List<User> getAllUserListPage(@Param("page") EasyPageInfo page, @Param("user") User user);

    @Select("select id,username,ch_name as chName,email,phone,create_time as createTime,status from monitor_user")
    public List<User> getAllUserList();

    @Select("select id,username,ch_name as chName,email,phone,create_time as createTime,status,password,roles,salt from monitor_user where username = #{name}")
    public User getUserByName(String name);

    public User selectUserRoles(Integer id);

    @Insert("insert into ss_user_role (user_id,role_id) values (#{0},#{1})")
    void saveUserRoles(Integer userid, String roleid);

    @Delete("delete from ss_user_role where user_id = #{id} ")
    void deleteUserRoles(Integer id);


    @Select("select * from monitor_model")
    public List<Node> getAllModelNode();

    @Select("select * from monitor_model where id = #{id}")
    public Node getNodeUrl(Integer id);

    //功能tree  model相关操作

    @Select("select id,text,url,parent_id as parentId,status from monitor_model")
    public List<Node> getAllTreeListPage(@Param("page") EasyPageInfo page);

    @Insert("insert into monitor_model(text,url,parent_id)" +
            " values(#{text},#{url},#{parentId})")
    public void saveModel(Node node);

    @Select("select * from monitor_model where id = #{id}")
    public Node getModelId(Integer id);

    @Update("update monitor_model set text = #{text}, url = #{url}, parent_id = #{parentId} " +
            " where id = #{id}")
    public int updateMode(Node node);

    @Delete("delete from monitor_model where id = #{id}")
    public int deleteModel(Integer id);

    @Select("select * from monitor_model")
    public List<Node> getAllListModelId();
}
