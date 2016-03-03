package org.david.rain.monitor.monitor.controller.server;

import org.david.rain.monitor.monitor.domain.ReturnType;
import org.david.rain.monitor.monitor.domain.TypeSetting;
import org.david.rain.monitor.monitor.service.server.ResponseService;
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
 * Created by czw on 13-12-10.
 */

@Controller
@RequestMapping(value = "/server/response", method = RequestMethod.GET)
public class ResponseController {

    Logger logger = LoggerFactory.getLogger(ResponseController.class);

    @Autowired
    ResponseService serverService;

    @RequestMapping("manage")
    public String typeManagePage() {
        return "server/typemanage";
    }

    @RequestMapping(value = "type/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addType(String typeCode) {
        serverService.insertServerReturnType(typeCode);
        return JsonUtil.commonResponse(true, "添加成功。");
    }

    @RequestMapping(value = "type/list", method = RequestMethod.POST)
    public
    @ResponseBody
    List<ReturnType> getTypeList() {
        return serverService.getReturnTypeList();
    }

    @RequestMapping(value = "type/setting/list", method = RequestMethod.POST)
    public
    @ResponseBody
    List<TypeSetting> getTypeSettings(ReturnType returnType) {
        return serverService.getTypeSettings(returnType);
    }


    @RequestMapping(value = "type/setting/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addSetting(TypeSetting typeSetting) {
        try {
            serverService.addTypeSetting(typeSetting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonUtil.commonResponse(false, "对不起，操作失败。");
        }
        return JsonUtil.commonResponse(true, "保存成功。");
    }


    @RequestMapping(value = "type/setting/update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateSetting(TypeSetting typeSetting) {
        try {
            int re = serverService.updateTypeSetting(typeSetting);
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

    @RequestMapping(value = "type/setting/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteSetting(Integer id) {
        try {
            int re = serverService.deleteTypeSetting(id);
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


    @RequestMapping(value = "type/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteType(String typeCode) {
        try {
            int re = serverService.deleteType(typeCode);
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


}
