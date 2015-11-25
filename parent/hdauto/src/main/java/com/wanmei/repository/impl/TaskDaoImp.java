package com.david.web.pppppp.repository.impl;

import com.david.web.pppppp.common.CommonList;
import com.david.web.pppppp.common.search.Search;
import com.david.web.pppppp.entity.Task;
import com.david.web.pppppp.repository.Idao;
import com.david.web.pppppp.repository.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gameuser
 * Date: 12-12-13
 * Time: 下午4:20
 * To change this template use File | Settings | File Templates.
 */
@Qualifier("TaskDaoImp")
@Component
public class TaskDaoImp implements TaskDao {
    private Idao idao;


    @Autowired
    public void setIdao(@Qualifier("daoImp") Idao idao) {
        this.idao = idao;
    }

    @Override
    public void deleteByUserId(Integer id) throws Exception {
        idao.update("delete from ss_task where id = ?");
    }

    @Override
    public Task findOne(Long id) throws SQLException {
        return idao.queryObject(Task.class, "select * from ss_task where id = ?", id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(Task entity) throws SQLException {
        idao.insert(entity);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Long id) throws SQLException {
        idao.update("delete from ss_task where id = ?", id);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Task> findAll() throws SQLException {
        return idao.queryObjects(Task.class, "select * from QRTZ_TRIGGERS");
    }

    @Override
    public CommonList pagination(Search search, Class<Task> taskClass) {
        return idao.pagination(search, taskClass);
    }

    @Override
    public Task findByTaskName(String triggerName) throws SQLException {

        return idao.queryObject(Task.class, "select trigger_name from QRTZ_TRIGGERS where trigger_name = ?", triggerName);
    }
}
