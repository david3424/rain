package org.david.rain.monitor.monitor.controller.server;

import org.david.rain.monitor.monitor.domain.RemindInfo;
import org.david.rain.monitor.monitor.service.server.RemindService;
import org.david.rain.monitor.monitor.util.JsonUtil;
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
 * Created by czw on 14-1-6.
 */


@Controller
@RequestMapping("/server/item/remind")
public class ItemRemindController {

    static Logger logger = LoggerFactory.getLogger(ItemRemindController.class);


    @Autowired
    RemindService remindService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addRemind(RemindInfo remindInfo) {
        try {
            remindService.addRemind(remindInfo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonUtil.commonResponse(false, "对不起，操作失败。");
        }
        return JsonUtil.commonResponse(true, "保存成功。");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateRemind(RemindInfo remindInfo) {
        try {
            int re = remindService.updateRemind(remindInfo);
            if (re > 0) {
                return JsonUtil.commonResponse(true, "保存成功。");
            } else {
                return JsonUtil.commonResponse(false, "保存失败。");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonUtil.commonResponse(false, "对不起，系统错误。");
        }

    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteRemind(Integer id) {
        try {
            int re = remindService.deleteRemind(id);
            if (re > 0) {
                return JsonUtil.commonResponse(true, "删除成功。");
            } else {
                return JsonUtil.commonResponse(false, "删除失败。");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonUtil.commonResponse(false, "对不起，系统错误。");
        }

    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public List<RemindInfo> getRemindListByItemId(Integer itemId) {
        return remindService.getRemindListByItemId(itemId);
    }
}
