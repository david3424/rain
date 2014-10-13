package com.wanmei.service.task;

import com.wanmei.common.CommonList;
import com.wanmei.common.search.Search;
import com.wanmei.entity.SendProperty;
import com.wanmei.entity.Task;
import com.wanmei.repository.SendPrizeDao;
import com.wanmei.repository.TaskDao;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class TaskService {


    private TaskDao taskDao;

    private SendPrizeDao sendPrizeDao;

    @Autowired
    public void setSendPrizeDao(@Qualifier("sendPrizeDao") SendPrizeDao sendPrizeDao) {
        this.sendPrizeDao = sendPrizeDao;
    }

    public Task getTask(Long id) throws SQLException {
        return taskDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void saveTask(Task entity) throws SQLException {
        taskDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void deleteTask(Long id) throws SQLException {
        taskDao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) //default 就是 required
    public void saveSendProperty(Task task) throws SQLException {
        SendProperty sendProperty = new SendProperty(task);
        sendPrizeDao.save(sendProperty);
    }

    public List<Task> getAllTask() throws Exception {
        return (List<Task>) taskDao.findAll();
    }

    public Page<Task> getUserTask(Long userId, Map<String, Object> filterParams, int pageNumber, int pageSize,
                                  String sortType) {
        /* PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
      Specification<Task> spec = buildSpecification(userId, filterParams);

      return taskDao.findAll(spec, pageRequest);*/
        return null;
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
        Sort sort = null;
        if ("auto".equals(sortType)) {
            sort = new Sort(Direction.DESC, "id");
        } else if ("title".equals(sortType)) {
            sort = new Sort(Direction.ASC, "title");
        }

        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }


    private Scheduler scheduler;
    private JobDetail jobDetail;

    @Autowired
    public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    @Autowired
    public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Autowired
    public void setTaskDao(@Qualifier("TaskDaoImp") TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public CommonList pagination(Search search, Class<Task> taskClass) {

        return taskDao.pagination(search, taskClass);
    }

    public void addSchedule(Task newTask) throws SQLException {

        String trigger_group = newTask.getDatasource() + "|" + newTask.getTrigger_group();
        String display_name = newTask.getDisplay_name();

        if (newTask.getTrigger_name() == null || newTask.getTrigger_name().trim().equals("")) {
            throw new RuntimeException("Trigger' name cannot be null");
        }
        String trigger_name = newTask.getTrigger_name().trim();
        try {

            saveSendProperty(newTask);
            scheduler.addJob(jobDetail, true);

            CronTrigger cronTrigger = new CronTrigger(trigger_name, trigger_group, jobDetail.getName(),
                    Scheduler.DEFAULT_GROUP);
            display_name = display_name.length() > 2 ? display_name : "0/" + display_name + " * * * * ?";
            System.out.println(display_name + "定时器添加成功。");
            cronTrigger.setCronExpression(new CronExpression(display_name));
            scheduler.scheduleJob(cronTrigger);
            pauseTask(trigger_name, trigger_group);
//			scheduler.rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public boolean deleteTask(String trigger_name, String trigger_group) throws Exception {
        String dataSource = trigger_group.replace('|', ',');
        dataSource = dataSource.split(",")[0];
        int re = sendPrizeDao.delete(trigger_name, dataSource);
        if (re > 0) {
            scheduler.pauseTrigger(trigger_name, trigger_group);//停止触发器
            return scheduler.unscheduleJob(trigger_name, trigger_group);//移除触发器
        }
        return false;
    }

    public void pauseTask(String name, String group) throws SchedulerException {
        scheduler.pauseTrigger(name, group);//暂停触发器
    }

    public void resumeTask(String name, String group) throws SchedulerException {
        scheduler.resumeTrigger(name, group);//恢复触发器
    }

    public Task findByTaskName(String triggerName) throws SQLException {

        return taskDao.findByTaskName(triggerName);
    }

    public int getOpenStatus() throws SQLException {
        return sendPrizeDao.queryMainSwitch();
    }


    public int openMainSwitch() throws SQLException
    {
        return sendPrizeDao.openMainSwitch();
    }

    public int closeMainSwitch() throws SQLException
    {
        return sendPrizeDao.closeMainSwitch();
    }
}
