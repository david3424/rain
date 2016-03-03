package org.david.rain.monitor.monitor.controller.server;

import org.david.rain.monitor.monitor.domain.ServerMonitorValue;
import org.david.rain.monitor.monitor.service.server.ServerMonitorService;
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
 * Created by czw on 14-1-8.
 */

@Controller
@RequestMapping("/server/item/response")
public class ItemMonitorSettingController {
    static Logger logger = LoggerFactory.getLogger(ItemRemindController.class);

    @Autowired
    ServerMonitorService serverMonitorService;

    @RequestMapping("list")
    @ResponseBody
    public List<ServerMonitorValue> getServerMonitorValueByItemId(Integer itemId) {
        return serverMonitorService.getServerMonitorValue(itemId);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSettingAttr(ServerMonitorValue setting) {
        try {
            serverMonitorService.addItemSettingAttr(setting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSettingAttr(ServerMonitorValue setting) {

        int re = serverMonitorService.updateItemSettingAttr(setting);

        if (re > 0) {
            return JsonUtil.commonResponse(true, "修改成功。");
        } else {
            return JsonUtil.commonResponse(false, "修改失败。");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteSettingAttr(Integer id) {

        int re = serverMonitorService.deleteItemSettingAttr(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功。");
        } else {
            return JsonUtil.commonResponse(false, "删除失败。");
        }
    }


}
