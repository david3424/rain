package org.david.rain.monitor.monitor.controller.data;

import org.david.rain.monitor.monitor.domain.OscillationCheckSetting;
import org.david.rain.monitor.monitor.domain.OscillationLog;
import org.david.rain.monitor.monitor.service.data.DataOscillationService;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by czw on 14-3-13.
 */

@Controller
@RequestMapping("/data/item/oscillation")
public class OscillationController {


    @Autowired
    DataOscillationService dataOscillationService;

    @RequestMapping("oslog")
    public String toItem(Model model,Integer itemId,String attrName) {
        model.addAttribute("itemId",itemId);
        model.addAttribute("attrName", attrName);
        return "data/osclog";
    }


    @RequestMapping("list")
    @ResponseBody
    public List<OscillationCheckSetting> getOscillationList(Integer itemId) {
        if (itemId == null || itemId == 0) {
            return new ArrayList<>(1);
        }
        return dataOscillationService.queryOscillationList(itemId);
    }

    @RequestMapping("log/list")
    @ResponseBody
    PaginationJsonObject<OscillationLog> getOscillationLogList(EasyPageInfo pageInfo, OscillationLog oscillationLog) {
        List<OscillationLog> list = dataOscillationService.getAllListPageLog(pageInfo, oscillationLog);
        return new PaginationJsonObject<>(list, pageInfo);
    }

//    @RequestMapping("search/list")
//    @ResponseBody
//    PaginationJsonObject<OscillationLog> getOscillationSearchList(EasyPageInfo pageInfo, String attrName) {
//        List<OscillationLog> list = dataOscillationService.getSearchListPage(pageInfo, attrName);
//        return new PaginationJsonObject<>(list, pageInfo);
//    }

    @RequestMapping("add")
    @ResponseBody
    public Map<String, Object> addOscillation(OscillationCheckSetting setting) {
        try {
            dataOscillationService.addOscillation(setting);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> modifyOscillation(OscillationCheckSetting setting) {
        return null;
    }


    @RequestMapping("pause")
    @ResponseBody
    public Map<String, Object> stopOscillation(Integer itemId, String attrName) {
        return null;
    }

}
