package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.Task;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {


    List<Task> getTaskList();

    List<Task> getMemberListPage(@Param("page") EasyPageInfo page);

    Task getTask(Integer taskId);

}
