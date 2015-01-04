package org.david.rain.dubbox.provider.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.david.rain.dubbox.provider.dao.utils.EasyPageInfo;
import org.david.rain.dubbox.provider.entity.Task;

import java.util.List;

public interface TaskMapper {


    List<Task> getTaskList();

    List<Task> getMemberListPage(@Param("page") EasyPageInfo page);

    @Select("select * from ss_task where id = #{taskId} ")
    Task getTask(@Param("taskId") Integer taskId);

}
