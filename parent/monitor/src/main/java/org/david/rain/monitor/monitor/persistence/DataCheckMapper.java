package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.DataCheckSetting;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by czw on 14-2-28.
 */
public interface DataCheckMapper {
    @Select("select * from data_monitor_check where item_id = #{id}")
    List<DataCheckSetting> getCheckerByItem(Integer id);

    @Insert("insert into data_monitor_check (item_id,checker_name,ch_name,memo,expression,abn_level,create_time,status)" +
            " values (#{itemId},#{checkerName},#{chName},#{memo},#{expression},#{abnLevel},#{createTime},1)")
    public void saveRule(DataCheckSetting checkSetting);

    @Update("update data_monitor_check set checker_name = #{checkerName}, ch_name = #{chName},memo = #{memo}, expression = #{expression}, abn_level = #{abnLevel} " +
            " where item_id = #{itemId} and checker_name = #{checkerName}")
    public int updateRule(DataCheckSetting checkSetting);

    @Update("update data_monitor_check set status = #{nowStatus} where item_id = #{setting.itemId} and checker_name=#{setting.checkerName} and status = #{setting.status}")
    public int updateRuleStatus(@Param("setting") DataCheckSetting setting, @Param("nowStatus") Integer nowStatus);

    @Delete("delete from data_monitor_check where item_id = #{itemId} and checker_name = #{checkerName}")
    public int deleteRule(@Param("itemId") Integer itemId, @Param("checkerName") String checkerName);

    /**
     * 如果这个检查项不用了，可以修改status 成 -1*
     */
    @Select("select * from data_monitor_check where item_id = #{itemId} and status > 0")
    public List<DataCheckSetting> getDataCheckSettingByItemId(Integer itemId);

}
