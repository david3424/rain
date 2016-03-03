package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.DataItem;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by czw on 14-2-24.
 */
public interface DataItemMapper {

    public List<DataItem> getAllListPage(@Param("page") EasyPageInfo page, @Param("dataItem") DataItem dataItem);

    public List<DataItem> selectAllDataItem();


    public List<DataItem> getExceptionList();


    public List<DataItem> getDataItemList();

    public DataItem queryDataItemById(Integer itemId);


    @Select("select * from data_monitor_item where item_name  = #{name}")
    public DataItem queryDataItemByName(String name);

    @Update("update data_monitor_item set turns = turns + 1 where id = #{itemId} and turns = #{turns}")
    public int turnTurns(@Param("itemId") Integer itemId, @Param("turns") Long turns);


    @Update("update data_monitor_item set status = -1 where id =#{itemId}")
    public int deleteItem(Integer itemId);

    @Update("update data_monitor_item set job_status = #{now} where id = #{itemId} and job_status  = #{before}")
    public int updateJobStatus(@Param("itemId") Integer itemId, @Param("now") Integer now, @Param("before") Integer before);

    @Update("update data_monitor_item set status = #{now} where id = #{itemId} and status  = #{before}")
    public int updateItemStatus(@Param("itemId") Integer itemId, @Param("now") Integer now, @Param("before") Integer before);

    @Update("update data_monitor_item set memo = #{memo} ,item_name = #{itemName},  item_name_ch = #{itemNameCh},begin_time = #{beginTime}" +
            ",end_time = #{endTime} , job_cron = #{jobCron}, data_source =#{dataSource} where id = #{id}")
    public int updateItemInfo(DataItem item);


    @Insert("insert into data_monitor_item(item_name,data_source,item_name_ch,begin_time,end_time,change_time,job_status,job_cron,user_id,memo," +
            "create_time,status) values (" +
            "#{itemName},#{dataSource},#{itemNameCh},#{beginTime},#{endTime},#{changeTime},#{jobStatus},#{jobCron},#{userId}," +
            "#{memo},#{createTime},#{status})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    public int insertDataItem(DataItem item);

}

