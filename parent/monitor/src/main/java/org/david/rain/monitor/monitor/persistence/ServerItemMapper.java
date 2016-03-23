package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.RichServerItem;
import org.david.rain.monitor.monitor.domain.ServerItem;
import org.david.rain.monitor.monitor.domain.TypeSetting;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by david on 13-12-10.
 */
public interface ServerItemMapper {


    @Select("        SELECT id, item_name as itemName,\n" +
            "        item_name_ch as itemNameCh,\n" +
            "        item_url as itemUrl,\n" +
            "        return_type as returnType,\n" +
            "        change_time as changeTime,\n" +
            "        create_time as createTime,\n" +
            "        job_status as jobStatus,\n" +
            "        job_cron as jobCron,\n" +
            "        user_id as userId,\n" +
            "        status\n" +
            "        from monitor_item")
    public List<ServerItem> getAllList();


    public List<ServerItem> getExceptionList();

    public List<ServerItem> getAllListPage(@Param("page") EasyPageInfo page, @Param("itemService") ServerItem itemService);

    @Insert("insert into monitor_item (item_name,item_name_ch,item_url,return_type,change_time,create_time,job_status,job_cron,user_id,status)" +
            "values(#{itemName},#{itemNameCh},#{itemUrl},#{returnType},#{changeTime},#{createTime},#{jobStatus},#{jobCron},#{userId},#{status})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = java.lang.Integer.class)
    public void saveItem(ServerItem serverItem);


    @Update("update monitor_item set item_name=#{itemName},item_name_ch =#{itemNameCh},item_url = #{itemUrl},return_type=#{returnType},job_cron=#{jobCron}" +
            ",user_id = #{userId} where id =#{id}")
    public int updateItem(ServerItem serverItem);


    @Delete("delete from monitor_item where id = #{id}")
    public int deleteItem(int id);


    @Select("select * from monitor_item where id =#{id}")
    public ServerItem getServerItemById(Integer id);


    @Insert("insert into monitor_item_status_log (item_name,item_url,return_type,status_begin," +
            "status_end,job_cron,status) " +
            "select item_name,item_url,return_type,change_time,#{statusEndTime},job_cron,status " +
            "from monitor_item" +
            " where id = #{itemId}")
    public int backStatusLog(@Param("itemId") Integer itemId, @Param("statusEndTime") String statusEndTime);


    @Update("update monitor_item set status = #{nowStatus}, change_time =#{nowTime} where id = #{itemId}")
    public int updateItemStatus(@Param("itemId") Integer itemId, @Param("nowTime") String nowTime, @Param("nowStatus") Integer nowStatus);


    public RichServerItem getRichServerItemById(int id);


    public List<TypeSetting> getTypeSettingByTypeCode(String typeCode);

    @Update("update monitor_item set job_status = #{newStatus} where id = #{id} and job_status = #{oldStatus}")
    public int updateJobStatus(@Param("id") int itemId, @Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus);
}
