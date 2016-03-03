package org.david.rain.monitor.monitor.controller.time;

import org.david.rain.monitor.monitor.domain.DataTimeSetting;
import org.david.rain.monitor.monitor.domain.DateTimeItem;
import org.david.rain.monitor.monitor.service.time.TimeItemService;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-13
 * Time: 下午1:58
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/time/item")
public class TimeItemController {
    static final Logger logger = LoggerFactory.getLogger(TimeItemController.class);

    @Autowired
    TimeItemService timeItemService;

    @RequestMapping("manager")
    public String toItem() {
        return "time/timemanager";
    }

    @RequestMapping("list/page")
    public
    @ResponseBody
    PaginationJsonObject<DateTimeItem> getDataItemList(EasyPageInfo pageInfo, DateTimeItem itemService) {
        try {
            if (!StringUtils.isEmpty(itemService.getStrategyName())) {
                String str = URLDecoder.decode(itemService.getStrategyName(), "UTF-8");
                itemService.setStrategyName(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<DateTimeItem> list = timeItemService.getTimeItems(pageInfo, itemService);
        return new PaginationJsonObject<>(list, pageInfo);
    }

    @RequestMapping("list")
    @ResponseBody
    public List<DateTimeItem> getTimePhaseStrategy() {
        return timeItemService.getTimeStrategyList();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addAttr(DateTimeItem attrSetting) {
        int re = timeItemService.saveTime(attrSetting);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "增加成功");
        } else {
            return JsonUtil.commonResponse(false, "增加失败");
        }
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(DateTimeItem checkSetting) {
        try {
            timeItemService.updateTime(checkSetting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败");
        }
        return JsonUtil.commonResponse(true, "修改成功");

    }


    @RequestMapping("attr/list")
    public
    @ResponseBody
    PaginationJsonObject<DataTimeSetting> getDataItemAttrList(EasyPageInfo pageInfo, Integer strategyId) {
        List<DataTimeSetting> list = timeItemService.getTimeAttrItems(pageInfo, strategyId);
        return new PaginationJsonObject<>(list, pageInfo);
    }

    @RequestMapping(value = "attr/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addItemAttr(DataTimeSetting attrSetting) {
        int re = timeItemService.saveTimeAttr(attrSetting);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "增加成功");
        } else {
            return JsonUtil.commonResponse(false, "增加失败");
        }
    }


    @RequestMapping(value = "attr/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAttr(DataTimeSetting checkSetting) {
        try {
            timeItemService.updateTimeAttr(checkSetting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败");
        }
        return JsonUtil.commonResponse(true, "修改成功");

    }

}
