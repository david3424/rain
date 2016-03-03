package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.DataTimeSetting;
import org.david.rain.monitor.monitor.domain.DateTimeItem;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-13
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public interface TimeItemMapper {

    public List<DateTimeItem> getAllListPage(@Param("page") EasyPageInfo page, @Param("itemService") DateTimeItem itemService);

    @Select("select * from data_phase_strategy")
    public List<DateTimeItem> getAllList();

    @Insert("insert into data_phase_strategy (strategy_name,memo,create_time,status)" +
            " values (#{strategyName},#{memo},#{createTime},0)")
    public int saveTime(DateTimeItem checkSetting);

    @Update("update data_phase_strategy set strategy_name = #{strategyName}, memo = #{memo},status = 0 " +
            " where id = #{id} ")
    public int updateTime(DateTimeItem checkSetting);


    @Select("select * from data_phase_detail where strategy_id= #{strategyId} and status!=-1")
    public List<DataTimeSetting> getAllListAttrPage(@Param("page") EasyPageInfo page, @Param("strategyId") Integer strategyId);

    @Insert("insert into data_phase_detail (strategy_id,phase_name,memo,phase_start,phase_end,tendency,create_time,status)" +
            " values (#{strategyId},#{phaseName},#{memo},#{phaseStart},#{phaseEnd},#{tendency},#{createTime},0)")
    public int saveTimeAttr(DataTimeSetting checkSetting);

    @Update("update data_phase_detail set  phase_start = #{phaseStart},phase_end = #{phaseEnd},memo = #{memo},tendency = #{tendency},status = 0 " +
            " where strategy_id = #{strategyId} and phase_name = #{phaseName}")
    public int updateTimeAttr(DataTimeSetting checkSetting);
}
