package com.david.web.wanmei.repository;


import com.david.web.wanmei.common.CommonList;
import com.david.web.wanmei.common.search.Search;
import com.david.web.wanmei.entity.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao{

	void deleteByUserId(Integer id) throws Exception;

    Task findOne(Long id) throws SQLException;

    void save(Task entity) throws SQLException;

    void delete(Long id) throws SQLException;

    List<Task> findAll() throws SQLException;

    CommonList pagination(Search search, Class<Task> taskClass);

    Task findByTaskName(String triggerName) throws SQLException;
}
