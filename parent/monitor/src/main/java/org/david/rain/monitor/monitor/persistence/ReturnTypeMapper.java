package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.ReturnType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by czw on 13-12-10.
 * <p/>
 * ReturnType 对应到java 需要一个解析器， common 目前是一个json 数据的解析，规则很简单，就是json的属性满足 < = > >= <= 标准设定值
 * 的然会就算正常。
 */
public interface ReturnTypeMapper {
    @Select("select * from server_monitor_return_type where id = #{id}")
    ReturnType getReturnType(Integer id);

    @Insert("insert into server_monitor_return_type ( type_code) values (#{typeCode})")
    void addReturnType(String typeCode);


    @Delete("delete from server_monitor_return_type where type_code = #{typeCode}")
    int deleteReturnTypeByCode(String typeCode);


    @Select("select id, type_code as typeCode, status from server_monitor_return_type")
    @ResultType(ReturnType.class)
    List<ReturnType> getAllTypes();

}
