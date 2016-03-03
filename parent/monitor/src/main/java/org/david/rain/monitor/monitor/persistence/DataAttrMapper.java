package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.DataAttrLog;
import org.david.rain.monitor.monitor.domain.DataAttrSetting;
import org.david.rain.monitor.monitor.domain.DataCheckLog;
import org.david.rain.monitor.monitor.domain.DataCheckSetting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by czw on 14-2-24.
 */
public interface DataAttrMapper {

    /**
     * 这个属性后期不能删除，只能增加新的，所以这里的status字段是用来控制这个属性是否可用
     * status = 1 表示可用， 0 表示不可用
     *
     * @param itemId
     * @return
     */
    @Select("select * from data_item_attr_setting where item_id = #{itemId} and status = 1")
    public List<DataAttrSetting> getDataAttrSettingByItemId(Integer itemId);


    @Insert("insert into data_item_attr_setting(item_id,attr_name,ch_name,memo,`sql`,data_source,status) values" +
            "(#{itemId},#{attrName},#{chName},#{memo},#{sql},#{dataSource},#{status})")
    public int insertDataAttr(DataAttrSetting attrSetting);


    @Delete("delete from data_item_attr_setting where item_id = #{itemId} and attr_name = #{attrName}")
    public int deleteDataAttr(@Param("itemId") Integer itemId, @Param("attrName") String attrName);

    @Insert("insert into data_monitor_check_log(item_id,checker_name,create_time,item_turns,status)" +
            " values (#{itemId},#{checkerName},#{createTime},#{itemTurns},#{status})")
    public int insertDataCheckLog(DataCheckLog log);


    @Select("select * from  data_monitor_check where item_id = #{itemId}")
    public List<DataCheckSetting> getDataCheckSetting(Integer itemId);

    @Insert("insert into data_item_attr_log(item_id,attr_name,ch_name,item_turns,result,status) values (" +
            "#{itemId},#{attrName},#{chName},#{itemTurns},#{result},#{status})")
    public int insertAttrLog(DataAttrLog log);
}
