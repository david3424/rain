package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.OscillationCheckSetting;
import org.david.rain.monitor.monitor.domain.OscillationLog;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by czw on 14-3-5.
 */
public interface OscillationMapper {
    public List<OscillationCheckSetting> querySettingList(@Param("itemId") Integer itemId);

    public List<OscillationLog> getAllListPageLog(@Param("page") EasyPageInfo page, @Param("oscillationLog") OscillationLog oscillationLog);

    public List<OscillationLog> getSearchListPage(@Param("page") EasyPageInfo page, @Param("attrName") String attrName);


    @Select("select * from data_oscillation_log where status = 0 limit #{n}")
    public List<OscillationLog> getLastNErrorLog(Integer n);


    @Select("select * from data_oscillation_log where item_id = #{itemId} and attr_name = #{attrName} and times <= #{times} + #{an} limit #{n}")
    public List<OscillationLog> getLastNLogByItemId(@Param("itemId") Integer itemId, @Param("attrName") String attrName,
                                                    @Param("times") Long times, @Param("n") Integer n, @Param("an") Integer an);


    @Update("update data_oscillation_log set status = #{status} where item_id = #{itemId} and attr_name = #{attrName}")
    public int updateOscillationStatus(@Param("itemId") Integer itemId, @Param("attrName") String attrName, @Param("status") Integer status);

    @Select("select * from data_oscillation_log where item_id = #{itemId} and attr_name = #{an}" +
            " order by id desc limit 1")
    public OscillationLog getLatestLog(@Param("itemId") Integer itemId, @Param("an") String attrName);


    @Insert("insert into data_oscillation_check(item_id,attr_name,oscillation,memo,phase_strategy,time_step,abn_level,status)" +
            "values(#{itemId},#{attrName},#{oscillation},#{memo},#{phaseStrategy},#{timeStep},#{abnLevel},#{status})")
    public void addOscillation(OscillationCheckSetting setting);


    @Insert("insert into data_oscillation_log(item_id,attr_name,item_turns,total,current_num,phase_strategy_id,phase_detail,times,create_time,create_time_long,status)" +
            "values(#{itemId},#{attrName},#{itemTurns},#{total},#{currentNum},#{phaseStrategyId},#{phaseDetail},#{times},#{createTime},#{createTimeLong},#{status})")
    public void addOscillationLog(OscillationLog log);


    /**
     * 查询最近2条正常的数据,如果是换了一个阶段，不接上个阶段
     * <p/>
     * 例如，p1 = 0-8  p2 = 9-23 9点不接昨天的23点了
     *
     * @param itemId
     * @param attrName
     * @return
     */
    @Select("select * from data_oscillation_log " +
            "where item_id = #{itemId} and attr_name = #{an} and status = 1 and phase_detail = #{phaseDetail} and times >= #{lowTimes} order by times desc limit 2")
    public List<OscillationLog> getLatestTwoLog(@Param("itemId") Integer itemId,
                                                @Param("an") String attrName,
                                                @Param("phaseDetail") String phaseDetail,
                                                @Param("lowTimes") Integer lowTimes);


    @Select("select * from data_oscillation_log where item_id = #{itemId} and attr_name = #{attrName}")
    public List<OscillationLog> getFirstTwoNormalLog(@Param("itemId") Integer itemId, @Param("attrName") String attrName);

}
