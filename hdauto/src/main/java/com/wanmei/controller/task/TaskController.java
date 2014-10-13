package com.wanmei.controller.task;

import com.google.common.collect.Maps;
import com.wanmei.common.CommonList;
import com.wanmei.common.search.Search;
import com.wanmei.entity.SendProperty;
import com.wanmei.entity.Task;
import com.wanmei.service.account.ShiroDbRealm;
import com.wanmei.service.task.TaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import javax.servlet.ServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * <p/>
 * List page     : GET /task/
 * Create page   : GET /task/create
 * Create action : POST /task/create
 * Delete action : get delete/{name}|{group}
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private static final int PAGE_SIZE = 10;

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

    static {
        sortTypes.put("start_time", "开始时间");
        sortTypes.put("trigger_group", "分组");
    }

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "")
    public String list(@RequestParam(value = "sortType", defaultValue = "start_time") String sortType,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Search search = new Search();
        search.setPageNo(pageNumber);
        search.setPageSize(PAGE_SIZE);
        search.addOrder(sortType, Search.SEARCH_DESC);
        Iterator<Map.Entry<String, Object>> it = searchParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            String key = e.getKey();
            if (e.getValue() != null && !((String) e.getValue()).isEmpty()) {
                if (key.equals("is_kefu") & e.getValue().equals("1")) {
                    search.addWhere(Search.SEARCH_AND, "trigger_name like 'kefu_sendprize_temp%'");
                } else if (key.equals("trigger_state")) {
                    if (e.getValue().equals("PAUSED")) {
                        search.addWhere(Search.SEARCH_AND, e.getKey() + " = '" + e.getValue() + "' ");
                    } else {
                        search.addWhere(Search.SEARCH_AND, "trigger_state in ('WAITING','ACQUIRED')");
                    }
                } else {
                    search.addWhere(Search.SEARCH_AND, e.getKey() + " = '" + e.getValue() + "' ");
                }
            }
        }

        search.addSelectSql("SELECT trigger_name,priority,trigger_state,trigger_type,TRIGGER_GROUP,next_fire_time,prev_fire_time,start_time,end_time,trigger_state as statu FROM QRTZ_TRIGGERS");
        search.addSelectCountSql("SELECT count(*) FROM QRTZ_TRIGGERS");
        CommonList results = taskService.pagination(search, Task.class);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("list", results);
        return "task/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm() {
        return "task/add";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Task newTask, RedirectAttributes redirectAttributes, Model model) throws SQLException {


        /**
         * 以下是对老代码的适配，等后期可以直接切换代码
         */
        newTask.setTrigger_name(newTask.getTable_name());

        newTask.setTrigger_type(newTask.getDatasource());


        if (newTask.getSend_type() == SendProperty.SendType.COUPON.value) {
            newTask.setTrigger_group("3");
        } else if (newTask.getSend_type() == SendProperty.SendType.COUPON_MULTI.value) {
            newTask.setTrigger_group("4");
        } else if (newTask.getRole_id_type() == 1) {
            newTask.setTrigger_group("1");
        } else {
            newTask.setTrigger_group("2");
        }

        taskService.addSchedule(newTask);

        redirectAttributes.addFlashAttribute("message", "创建成功");
        return "redirect:/task/";
    }


    /**
     * 删除一个定时器
     *
     * @param name
     * @param group
     * @param redirectAttributes
     * @return
     * @throws java.sql.SQLException
     * @throws SchedulerException
     */
    @RequestMapping(value = "delete/{name}@{group}")
    public String delete(@PathVariable("name") String name, @PathVariable("group") String group, RedirectAttributes redirectAttributes) throws Exception, SchedulerException {
        boolean result = taskService.deleteTask(name, group);
        redirectAttributes.addFlashAttribute("message", result ? "删除任务成功。" : "删除任务失败。");
        return "redirect:/task/";
    }

    /**
     * 暂停一个定时器
     *
     * @param name
     * @param group
     * @param redirectAttributes
     * @return
     * @throws java.sql.SQLException
     * @throws SchedulerException
     */
    @RequestMapping(value = "pause/{name}@{group}@{status}")
    @ResponseBody
    public String pause(@PathVariable("name") String name, @PathVariable("status") String status, @PathVariable("group") String group, RedirectAttributes redirectAttributes) throws SQLException, SchedulerException {
        if (StringUtils.equals(status, "PAUSED")) {
            taskService.resumeTask(name, group);
        } else {
            taskService.pauseTask(name, group);
        }
        return "true";
    }


    /**
     * Ajax请求校验TaskName是否唯一。
     */
    @RequestMapping(value = "checkTaskName", method = RequestMethod.GET)
    @ResponseBody
    public String checkTaskName(@RequestParam("table_name") String table_name) throws Exception {
        if (taskService.findByTaskName(table_name) == null) {
            return "true";
        } else {
            return "false";
        }
    }


    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) throws SQLException {
        model.addAttribute("task", taskService.getTask(id));
        model.addAttribute("action", "update");
        return "task/taskForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("preloadTask") Task task, RedirectAttributes redirectAttributes) throws SQLException {
        taskService.saveTask(task);
        redirectAttributes.addFlashAttribute("message", "更新任务成功");
        return "redirect:/task/";
    }

    /**
     * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
     */
    @ModelAttribute("preloadTask")
    public Task getTask(@RequestParam(value = "id", required = false) Long id) throws SQLException {
        if (id != null) {
            return taskService.getTask(id);
        }
        return null;
    }

    /**
     * 取出Shiro中的当前用户Id.
     */
    private Integer getCurrentUserId() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) currentUser.getPrincipal();
        return user.id;
    }

    @RequestMapping(value = "mainSwitch", method = RequestMethod.GET)
    public String mainSwitch(Model model) throws SQLException {
        model.addAttribute("status", taskService.getOpenStatus());
        return "task/mainSwitch";
    }

    @RequestMapping(value = "closeMainSwitch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> closeMainSwitch() throws SQLException {
        int re = taskService.closeMainSwitch();
        Map<String, Object> json = new HashMap<String, Object>();
        if (re > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        return json;
    }

    @RequestMapping(value = "openMainSwitch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> openMainSwitch() throws SQLException {
        int re = taskService.openMainSwitch();
        Map<String, Object> json = new HashMap<String, Object>();
        if (re > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        return json;
    }

}
