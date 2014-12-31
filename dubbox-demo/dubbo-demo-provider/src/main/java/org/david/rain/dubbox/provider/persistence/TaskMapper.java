package org.david.rain.dubbox.provider.persistence;

import org.apache.ibatis.annotations.Param;
import org.david.rain.dubbox.provider.dao.utils.EasyPageInfo;
import org.david.rain.dubbox.provider.entity.Task;

import java.util.List;

public interface TaskMapper {


    List<Task> getTaskList();

    List<Task> getMemberListPage(@Param("page") EasyPageInfo page);

    Task getTask(Integer taskId);

}
