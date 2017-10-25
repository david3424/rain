package org.david.rain.cloud.start.web;

import org.david.rain.cloud.start.dao.mapper.KoTaskMapper;
import org.david.rain.cloud.start.pojo.KoTask;
import org.david.rain.common.exception.ErrorCode;
import org.david.rain.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xdw9486
 */
@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    KoTaskMapper koTaskMapper;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<KoTask> getTaskList() {
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        return koTaskMapper.findAllTasks();
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute KoTask task) {
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        koTaskMapper.insert(task);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public KoTask getTask(@PathVariable Long id) {
        // url中的id可通过@PathVariable绑定到函数的参数中
        throw new ServiceException(ErrorCode.SERVICE_EXCEPTION, "测试异常");
//        return koTaskMapper.findByTaskId(id + "");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putTask(@PathVariable Long id, @ModelAttribute KoTask koTask) {
        KoTask task = koTaskMapper.findByTaskId(id + "");
        task.setRemark(koTask.getRemark());
        task.setUpdatedTime(koTask.getUpdatedTime());
        koTaskMapper.updateByPrimaryKey(task);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteTask(@PathVariable Long id) {
        koTaskMapper.deleteByPrimaryKey(Math.toIntExact(id));
        return "success";
    }

}