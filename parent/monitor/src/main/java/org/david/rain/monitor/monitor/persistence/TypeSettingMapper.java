package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.ReturnType;
import org.david.rain.monitor.monitor.domain.TypeSetting;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by czw on 13-12-10.
 * Setting 是一个返回值的默认类型，相当于模板的作用，每个监控项都可以有自己的设定
 */
public interface TypeSettingMapper {

    List<TypeSetting> getTypeSettings(ReturnType returnType);

    @Select("select id, type_code as typeCode,\n" +
            "        attribute_name as attributeName,\n" +
            "        judge_method as judgeMethod,\n" +
            "        health_value as healthValue,\n" +
            "        default_level as defaultLevel,\n" +
            "        status " +
            "   from server_monitor_return_type_settings where type_code = #{typeCode}")
    List<TypeSetting> getTypeSettingsByTypeCode(String typeCode);

    @Insert("insert into server_monitor_return_type_settings(type_code,attribute_name,judge_method,health_value,default_level)" +
            "values(#{typeCode},#{attributeName},#{judgeMethod},#{healthValue},#{defaultLevel})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    int addTypeSetting(TypeSetting typeSetting);

    @Update("update server_monitor_return_type_settings set " +
            "attribute_name = #{attributeName}," +
            "judge_method=#{judgeMethod}," +
            "health_value=#{healthValue}," +
            "default_level=#{defaultLevel} where id=#{id}")
    int updateTypeSetting(TypeSetting typeSetting);

    @Delete("delete from server_monitor_return_type_settings where id = #{id}")
    int deleteTypeSettingById(Integer id);


    @Delete("delete from server_monitor_return_type_settings where type_code = #{typeCode}")
    int deleteTypeByTypeCode(String typeCode);

}
