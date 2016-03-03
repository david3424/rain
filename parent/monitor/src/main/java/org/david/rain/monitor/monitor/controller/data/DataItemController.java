package org.david.rain.monitor.monitor.controller.data;

import org.david.rain.monitor.monitor.domain.DataAttrSetting;
import org.david.rain.monitor.monitor.domain.DataCheckSetting;
import org.david.rain.monitor.monitor.domain.DataItem;
import org.david.rain.monitor.monitor.service.data.*;
import org.david.rain.monitor.monitor.shiro.MyRealm;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by czw on 14-3-6.
 */

@Controller
@RequestMapping("/data/item")
public class DataItemController {

    static final Logger logger = LoggerFactory.getLogger(DataItemController.class);

    @Autowired
    DataItemService dataItemService;


    @Autowired
    DataAttrService dataAttrService;

    @Autowired
    DataCheckService dataCheckService;

    @RequestMapping("manage")
    public String toItem() {
        return "data/dataitemmanager";
    }

    @RequestMapping("test")
    public String test() {
        return "test";
    }

    @RequestMapping("list")
    public
    @ResponseBody
    PaginationJsonObject<DataItem> getDataItemList(EasyPageInfo pageInfo, DataItem dataItem) {
        List<DataItem> list = dataItemService.getItemPageList(pageInfo, dataItem);
        return new PaginationJsonObject<>(list, pageInfo);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addDataItem(DataItem item) {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            MyRealm.ShiroUser shiroUser = (MyRealm.ShiroUser) currentUser.getPrincipals().getPrimaryPrincipal();
            item.setUserId(shiroUser.id);
            dataItemService.saveDataItem(item);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功 and 活动监控默认开启。");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateDataItem(DataItem item) {
        try {
            dataItemService.updateDataItem(item);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteItem(Integer id) {
        int re = dataItemService.deleteDataItem(id);
        if (re == 0)
            return JsonUtil.commonResponse(false, "删除失败");
        else return JsonUtil.commonResponse(true, "删除成功");
    }

    @RequestMapping(value = "job/start", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> startJob(Integer id) {

        int re = dataItemService.startJob(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "开启成功。");
        } else {
            return JsonUtil.commonResponse(false, "开启失败。");
        }
    }

    @RequestMapping(value = "job/pause", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pauseJob(Integer id) {
        int re = dataItemService.pauseJob(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "暂停成功。");
        } else {
            return JsonUtil.commonResponse(false, "暂停失败。");
        }
    }


    @RequestMapping(value = "attr/list", method = RequestMethod.POST)
    public
    @ResponseBody
    List<DataAttrSetting> getDataAttrList(Integer itemId) {
        if (itemId == null || itemId == 0) {
            return new ArrayList<>(1);
        }
        return dataAttrService.queryDataAttrSettingByItemId(itemId);
    }

    @RequestMapping(value = "attr/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addAttr(DataAttrSetting attrSetting) {
        int re = dataAttrService.insertDataAttrSetting(attrSetting);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "增加成功");
        } else {
            return JsonUtil.commonResponse(false, "增加失败");
        }
    }


    @RequestMapping(value = "attr/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteAttr(Integer itemId, String attrName) {
        int re = dataAttrService.deleteDataAttrSetting(itemId, attrName);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功");
        } else {
            return JsonUtil.commonResponse(false, "删除失败");
        }
    }

    //检查项属性 添加  by zj
    @RequestMapping(value = "rule/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRule(DataCheckSetting checkSetting) {
        Set<String> set = new HashSet<String>();
        Map<String, Long> valueMap = new HashMap<>();
        //保存之前检查输入的表达式是否符合要求 1，首先查询出选中项的属性值
        List<DataAttrSetting> list = dataAttrService.queryDataAttrSettingByItemId(checkSetting.getItemId());
        if (list == null) {
            return JsonUtil.commonResponse(false, "查询属性异常，请检查后再试。");
        }
        for (DataAttrSetting dataAttrSetting : list) {
            set.add(dataAttrSetting.getAttrName());
        }
        try {
            List<String> tokens = ExpressionCheckUtil.tokenizer(checkSetting.getExpression());
            if (tokens == null) {
                return JsonUtil.commonResponse(false, "表达式为空");
            }
            for (String str : tokens) {
                if (!ExpressionUtil.CHECK_ATTR_SYMBOLS.contains(str) && !ExpressionUtil.isNumber(str) && !set.contains(str)) {
                    System.out.println(ExpressionUtil.CHECK_ATTR_SYMBOLS.contains(str));
                    return JsonUtil.commonResponse(false, "您输入的表达式有误，属性值[" + str + "]不存在。");
                } else {
                    valueMap.put(str, new Long(1));
                }
            }
            //用于检测表达式规则是否正常  抛出异常---即出现错误提示
            ExpressionCheckUtil.isWright(checkSetting, valueMap);

            dataCheckService.saveRule(checkSetting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "表达式有误,错误提示：" + "{" + e.getMessage() + "}");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }

    @RequestMapping(value = "rule/list", method = RequestMethod.POST)
    public
    @ResponseBody
    List<DataCheckSetting> getDataRuleList(Integer itemId) {
        if (itemId == null || itemId == 0) {
            return new ArrayList<>(1);
        }
        return dataCheckService.queryDataCheckSettingByItemId(itemId);
    }

    @RequestMapping(value = "rule/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteRule(Integer itemId, String checkerName) {
        int re = dataCheckService.deleteRule(itemId, checkerName);
        if (re > 0)
            return JsonUtil.commonResponse(false, "删除失败");
        else return JsonUtil.commonResponse(true, "删除成功");
    }

    @RequestMapping(value = "rule/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(DataCheckSetting checkSetting) {
        Set<String> set = new HashSet<String>();
        Map<String, Long> valueMap = new HashMap<>();
        //保存之前检查输入的表达式是否符合要求 1，首先查询出选中项的属性值
        List<DataAttrSetting> list = dataAttrService.queryDataAttrSettingByItemId(checkSetting.getItemId());
        if (list == null) {
            return JsonUtil.commonResponse(false, "查询属性异常，请检查后再试。");
        }
        for (DataAttrSetting dataAttrSetting : list) {
            set.add(dataAttrSetting.getAttrName());
        }
        try {
            List<String> tokens = ExpressionCheckUtil.tokenizer(checkSetting.getExpression());
            if (tokens == null) {
                return JsonUtil.commonResponse(false, "表达式为空");
            }
            for (String str : tokens) {
                if (!ExpressionUtil.CHECK_ATTR_SYMBOLS.contains(str) && !ExpressionUtil.isNumber(str) && !set.contains(str)) {
                    System.out.println(ExpressionUtil.CHECK_ATTR_SYMBOLS.contains(str));
                    return JsonUtil.commonResponse(false, "您输入的表达式有误，属性值[" + str + "]不存在。");
                } else {
                    valueMap.put(str, new Long(1));
                }
            }
            //用于检测表达式规则是否正常  抛出异常---即出现错误提示
            ExpressionCheckUtil.isWright(checkSetting, valueMap);

            dataCheckService.updateRule(checkSetting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败，表达式有误,错误提示：" + "{" + e.getMessage() + "}");
        }
        return JsonUtil.commonResponse(true, "修改成功");
    }
}
