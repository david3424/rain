package org.david.rain.monitor.monitor.controller.send;

import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.service.server.SendPrizeService;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
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
 * Created by david
 * * on 13-12-13.
 */

@Controller
@RequestMapping(value = "/sendprize", method = RequestMethod.GET)
public class SendPrizeController {
    Logger logger = LoggerFactory.getLogger(SendPrizeController.class);

    @Autowired
    SendPrizeService sendPrizeService;


    @RequestMapping("manage")
    public String typeManagePage() {
        return "sendprize/sendprize";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public PaginationJsonObject<SendPrize> list(EasyPageInfo pageInfo, SendPrize sendPrize) {
        List<SendPrize> list = sendPrizeService.getItemPageList(pageInfo, sendPrize);
        return new PaginationJsonObject<>(list, pageInfo);
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(SendPrize sendPrize) {
        sendPrize.setStatus(0);
        sendPrize.setCreateTime(DateUtils.getCurrentFormatDateTime());
        sendPrize.setPrizememo("默认");
        sendPrize.setSendInterface(0);
        try {
            sendPrize.setSendCheck(MonitorConst.getCurrentUser().id); //check当成userId来用
            sendPrizeService.saveSendPrize(sendPrize);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSendPrize(SendPrize sendPrize) {
        sendPrize.setSendCheck(MonitorConst.getCurrentUser().id); //check当成userId来用
        int re;
        try {
            re = sendPrizeService.updateSendPrize(sendPrize);
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
    public Map<String, Object> deleteItem(SendPrize sendPrize) {

        int re = sendPrizeService.deleteSendPrize(sendPrize);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功。");
        } else {
            return JsonUtil.commonResponse(false, "删除失败。");
        }
    }

    @RequestMapping(value = "job/start", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> startJob(SendPrize sendPrize) {

        int re = sendPrizeService.startJob(sendPrize);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "开启成功。");
        } else {
            return JsonUtil.commonResponse(false, "开启失败。");
        }
    }

    @RequestMapping(value = "job/pause", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pauseJob(SendPrize sendPrize) {

        int re = sendPrizeService.pauseJob(sendPrize);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "暂停成功。");
        } else {
            return JsonUtil.commonResponse(false, "暂停失败。");
        }
    }
}
