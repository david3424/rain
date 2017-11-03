package org.david.rain.boot.start.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.david.rain.boot.start.dao.mapper.king.KoTaskMapper;
import org.david.rain.boot.start.pojo.KoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author xdw9486
 */
@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    KoTaskMapper koTaskMapper;

    @ApiIgnore
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @ApiOperation(value = "获取任务列表", notes = "getTaskList")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<KoTask> getTaskList() {
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        return koTaskMapper.findAllTasks();
    }

    @ApiOperation(value = "创建任务", notes = "根据Task对象创建任务")
    @ApiImplicitParam(name = "task", value = "任务详细实体task", required = true, dataType = "KoTask")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute KoTask task) {
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        koTaskMapper.insert(task);
        return "success";
    }

    @ApiOperation(value = "获取单个任务", notes = "根据ID获取")
    @ApiImplicitParam(name = "id", value = "任务ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public KoTask getTask(@PathVariable Long id) {
        // url中的id可通过@PathVariable绑定到函数的参数中
//        throw new ServiceException(ErrorCode.SERVICE_EXCEPTION, "测试异常");
        return koTaskMapper.findByTaskId(id + "");
    }

    @ApiOperation(value = "更新任务详细信息", notes = "根据url的id来指定更新对象，并根据传过来的task信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "task", value = "任务详细实体task", required = true, dataType = "KoTask")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putTask(@PathVariable Long id, @ModelAttribute KoTask koTask) {
        KoTask task = koTaskMapper.findByTaskId(id + "");
        task.setRemark(koTask.getRemark());
        task.setUpdatedTime(koTask.getUpdatedTime());
        koTaskMapper.updateByPrimaryKey(task);
        return "success";
    }


    @ApiOperation(value = "删除任务", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "任务ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteTask(@PathVariable Long id) {
        koTaskMapper.deleteByPrimaryKey(Math.toIntExact(id));
        return "success";
    }

}