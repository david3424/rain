package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.RemindInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by czw on 14-3-12.
 */
public interface DataRemindMapper {
    @Insert("insert into data_monitor_remind (item_id,remind_type,user_id,create_time,status) " +
            "values (#{itemId},#{remindType},#{userId},#{createTime},#{status})")
    public void insertRemind(RemindInfo remindInfo);


    @Update("update data_monitor_remind set remind_type = #{remindType} where id=#{id}")
    public int updateRemind(RemindInfo remindInfo);

    @Delete("delete from data_monitor_remind where id = #{id}")
    public int deleteRemind(Integer id);

    @Select("select a.id,a.item_id as itemId, a.remind_type as remindType, b.ch_name as chName, b.phone, b.email" +
            ", a.user_id as userId from data_monitor_remind a, monitor_user b where a.user_id =  b.id and a.item_id = #{itemId}")
    public List<RemindInfo> getRemindListByItem(Integer itemId);
}
