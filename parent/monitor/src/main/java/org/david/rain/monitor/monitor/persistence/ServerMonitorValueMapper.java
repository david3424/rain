package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.ServerMonitorValue;
import org.david.rain.monitor.monitor.domain.TypeSetting;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by david
 * * on 13-12-30.
 */
public interface ServerMonitorValueMapper {

    @Select("select id,item_id as itemId," +
            "type_code as typeCode," +
            "attribute_name as attributeName," +
            "judge_method as judgeMethod," +
            "health_value as healthValue," +
            "abn_times as abnTimes," +
            "abn_level as abnLevel," +
            "status_begin as statusBegin," +
            "status from server_monitor_values where item_id = #{itemId}")
    public List<ServerMonitorValue> getServerMonitorValueByItemId(Integer itemId);


    @Select("select id,item_id as itemId," +
            "type_code as typeCode," +
            "attribute_name as attributeName," +
            "judge_method as judgeMethod," +
            "health_value as healthValue," +
            "abn_times as abnTimes," +
            "abn_level as abnLevel," +
            "status_begin as statusBegin," +
            "status from server_monitor_values where item_id = #{itemId} and attribute_name = #{attributeName}")
    public ServerMonitorValue getServerMonitorValue(@Param("itemId") Integer itemId, @Param("attributeName") String attributeName);


    @Insert("insert into server_monitor_values (item_id,type_code,attribute_name,judge_method,health_value,abn_times,abn_level,status_begin," +
            "status) values (#{itemId},#{setting.typeCode},#{setting.attributeName},#{setting.judgeMethod},#{setting.healthValue}," +
            "0,#{setting.defaultLevel},#{beginTime},#{status})")
    public void insertServerMonitorValueBySetting(@Param("itemId") Integer itemId, @Param("setting") TypeSetting setting,
                                                  @Param("beginTime") String statusBeginTime, @Param("status") Integer status);

    @Insert("insert into server_monitor_values (item_id,type_code,attribute_name,judge_method,health_value,abn_times,abn_level,status_begin," +
            "status) values (#{itemId},#{typeCode},#{attributeName},#{judgeMethod},#{healthValue}," +
            "#{abnTimes},#{abnLevel},#{statusBegin},#{status})")
    public void insertServerMonitorValue(ServerMonitorValue serverMonitorValue);


    @Update("update server_monitor_values set abn_times = abn_times + 1 where item_id = #{itemId} and attribute_name = #{attributeName}")
    public int updateMonitorValuesAbnormal(@Param("itemId") Integer itemId, @Param("attributeName") String attributeName);

    @Update("update server_monitor_values set abn_times = 0,status_begin = #{statusBegin} ,status = #{status} " +
            "where item_id = #{itemId} and attribute_name = #{attributeName}")
    public int updateMonitorValueToHealth(@Param("itemId") Integer itemId, @Param("status") Integer status,
                                          @Param("attributeName") String attributeName, @Param("statusBegin") String statusBegin);


    @Update("update server_monitor_values set health_value = #{healthValue}, abn_level = #{abnLevel}, judge_method = #{judgeMethod} where id = #{id}")
    public int updateMonitorValue(ServerMonitorValue value);

    @Delete("delete from server_monitor_values where id = #{id}")
    public int deleteMonitorValue(Integer id);

    @Update("update server_monitor_values set abn_times = 1,status_begin = #{statusBegin},status = #{status}" +
            " where item_id = #{itemId} and attribute_name = #{attributeName}")
    public int updateMonitorValuesToAbnormal(@Param("itemId") Integer itemId, @Param("attributeName") String attributeName,
                                             @Param("status") Integer status, @Param("statusBegin") String statusBegin);

    @Insert("insert into server_monitor_values_log (item_id,type_code,attribute_name,judge_method,health_value,abn_times,abn_level,status_begin,status_end,status) " +
            "select item_id,type_code,attribute_name,judge_method,health_value,abn_times,abn_level,status_begin,#{statusEndTime},status" +
            " from server_monitor_values where item_id = #{itemId} and attribute_name = #{attributeName} ")
    public void saveMonitorValueLog(@Param("itemId") Integer itemId, @Param("attributeName") String attributeName,
                                    @Param("statusEndTime") String statusEndTime);
}
