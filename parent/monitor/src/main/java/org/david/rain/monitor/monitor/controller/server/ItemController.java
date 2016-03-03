package org.david.rain.monitor.monitor.controller.server;

import org.david.rain.monitor.monitor.domain.ServerItem;
import org.david.rain.monitor.monitor.service.server.ItemService;
import org.david.rain.monitor.monitor.shiro.MyRealm;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by czw on 13-12-13.
 */

@Controller
@RequestMapping(value = "/server/item", method = RequestMethod.GET)
public class ItemController {
    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @RequestMapping("manage")
    public String typeManagePage() {
        return "server/itemmanage";
    }


    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public PaginationJsonObject<ServerItem> list(EasyPageInfo pageInfo, ServerItem serverItem) {
        List<ServerItem> list = itemService.getItemPageList(pageInfo, serverItem);
        return new PaginationJsonObject<>(list, pageInfo);
    }

    @RequestMapping("list/exception")
    public
    @ResponseBody
    List<ServerItem> getExceptionList() {
        return itemService.getExceptionList();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addItem(ServerItem item) {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            MyRealm.ShiroUser shiroUser = (MyRealm.ShiroUser) currentUser.getPrincipals().getPrimaryPrincipal();
            item.setUserId(shiroUser.id);
            itemService.saveServerItem(item);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateItem(ServerItem item) {

        int re = 0;
        try {
            re = itemService.updateServerItem(item);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败,系统异常。");
        }
        if (re > 0) {
            return JsonUtil.commonResponse(true, "修改成功。");
        } else {
            return JsonUtil.commonResponse(false, "修改失败。");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteItem(Integer id) {

        int re = itemService.deleteServerItem(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功。");
        } else {
            return JsonUtil.commonResponse(false, "删除失败。");
        }
    }

    @RequestMapping(value = "job/start", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> startJob(Integer id) {

        int re = itemService.startJob(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "开启成功。");
        } else {
            return JsonUtil.commonResponse(false, "开启失败。");
        }
    }

    @RequestMapping(value = "job/pause", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pauseJob(Integer id) {

        int re = itemService.pauseJob(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "暂停成功。");
        } else {
            return JsonUtil.commonResponse(false, "暂停失败。");
        }
    }

}
