package org.david.rain.act.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.david.rain.act.entity.Task;
import org.david.rain.act.utils.EasyPageInfo;

import java.util.List;

public interface TaskMapper {


    List<Task> getTaskList();

    List<Task> getMemberListPage(@Param("page") EasyPageInfo page);

    Task getTask(Integer taskId);
    
    @Update("update ss_task set title = #{title} where id = #{id} ")
    void updateTaskById(Task task);

}
