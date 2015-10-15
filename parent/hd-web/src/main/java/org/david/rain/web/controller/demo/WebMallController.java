package org.david.rain.web.controller.demo;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.david.rain.common.components.util.ActionUtil;
import org.david.rain.common.util.DateUtils;
import org.david.rain.common.util.ObjectResponseWrapper;
import org.david.rain.web.service.hdinterface.entity.UserBean;
import org.david.rain.web.util.validate.SwitchValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 */
@Controller
@RequestMapping("/demo/webmall")
public class WebMallController {

    Logger logger = LoggerFactory.getLogger(WebMallController.class);

    @Autowired
    WebMallService service;

    @RequestMapping(value = "/mycenter", method = RequestMethod.GET)
    public String mycenter(Model model,Integer type) throws SQLException {
        String username = ActionUtil.getUserName();
        if (!StringUtils.isEmpty(username)) {
            UserBean userBean = service.getUserBean(username);
            model.addAttribute("stoneCount", userBean.getTotalscore() - userBean.getUsedscore());
            model.addAttribute("type", type);
        }
        return "demo/webmall/mycenter";
    }

    /**
     * 到抢码页面
     * 判断剩余时间 如果是 9点之前 那么判断是到当天9点的剩余时间，如果9点-21点剩余时间为0，如果21点以后剩余时间为到第二天9点的时间
     * @param model
     * @return
     * @throws SQLException
     * [10：00，12：00）点 [14：00，16：00） [19：00，21：00）每天分三个时段抢
     */
    @RequestMapping(value = "/toexchange", method = RequestMethod.GET)
    public String toexchange(Model model) throws SQLException {
        String username = ActionUtil.getUserName();
        if (!StringUtils.isEmpty(username)) {
            UserBean userBean = service.getUserBean(username);
            model.addAttribute("stoneCount", userBean.getTotalscore() - userBean.getUsedscore());
            model.addAttribute("myCode",service.getCode(username));
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);//小时
        String curDate = sf.format(c.getTime());
        logger.debug("createtime:"+ DateUtils.getCurrentFormatDateTime());
        logger.debug("hour:"+hour);
        logger.debug("curDate:"+curDate);
        if(hour<10){
            model.addAttribute("endtime", curDate + " 10:00:00");
        }else if(hour<12){
            model.addAttribute("endtime", "");
        }else if(hour<14){
            model.addAttribute("endtime", curDate + " 14:00:00");
        }else if(hour<16){
            model.addAttribute("endtime", "");
        }else if(hour<19){
            model.addAttribute("endtime", curDate + " 19:00:00");
        }else if(hour<21){
            model.addAttribute("endtime", "");
        }else{
            c.add(Calendar.DAY_OF_MONTH, 1);
            String addDateTime = sf.format(c.getTime());
            model.addAttribute("endtime", addDateTime + " 10:00:00");
        }
        return "demo/webmall/code";
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) throws SQLException {
        String username = ActionUtil.getUserName();
        String date = DateUtils.getCurrentFormatDate();
        if (!StringUtils.isEmpty(username)) {
            UserBean userBean = service.getUserBean(username);
            model.addAttribute("stoneCount", userBean.getTotalscore() - userBean.getUsedscore());
            model.addAttribute("signLogs", service.getSignLogs(username));
            model.addAttribute("today", date);
            model.addAttribute("isSubmitInfo", StringUtils.isBlank(service.getUserBean(username).getPhone()) ? 0 : 1);
            model.addAttribute("isSignToday", service.isSignToday(username, date) ? 1 : 0); //当天是否已签
            model.addAttribute("isWechatAttention", service.isWechatAttention(username) ? 1 : 0);
            model.addAttribute("isAnswer", service.isAnswer(username) ? 1 : 0);
            model.addAttribute("isDownload", service.isDownload(username) ? 1 : 0);


        }
        return "demo/webmall/index";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @SwitchValidate("hex_webmall_status")
    public
    @ResponseBody
    Map<String, Object> submit(UserBean userInfo) throws Exception {
        try {
            String username = ActionUtil.getUserName();
            UserBean userBean = service.getUserBean(username);
            if (null == userBean) {
                return ObjectResponseWrapper.commonResponse(false, 0, "网络异常，请重新登录。");
            }
            if (StringUtils.isBlank(userInfo.getTruename()) || StringUtils.isBlank(userInfo.getAddress())) {
                return ObjectResponseWrapper.commonResponse(false, 0, "请完善数据之后再提交。");
            }
//            int type=(StringUtils.isBlank(userBean.getTruename())||StringUtils.isBlank(userInfo.getPhone()))?WebMallConst.FIRST_SUBMIT:WebMallConst.NOT_FIRST_SUBMIT;
            return service.submit(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponseWrapper.commonResponse(false, -1, "网络中断，请稍后重试！");
        }
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    @SwitchValidate("hex_webmall_status")
    public
    @ResponseBody
    Map<String, Object> answer(Answer answer) throws Exception {
        try {
            if(answer==null||answer.isEmpty()){
                return ObjectResponseWrapper.commonResponse(false, -1, "请回答HEX百科问题！");
            }
            return service.answer(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponseWrapper.commonResponse(false, -1, "网络中断，请稍后重试！");
        }
    }



    /**
     * 魔石明细
     *
     * @param pageNo
     * @param pageSize
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detailList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> list(@RequestParam Integer pageNo, @RequestParam Integer pageSize, Integer type) throws Exception {
        String username = ActionUtil.getUserName();
        return ObjectResponseWrapper.createPagenationMap(service.queryStoneDetailList(pageNo, pageSize, type, username));
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @SwitchValidate("hex_webmall_status")
    public
    @ResponseBody
    Map<String, Object> getUser() throws Exception {
        try {
            String username = ActionUtil.getUserName();
            UserBean userBean = service.getUserBean(username);
            Map<String, Object> result = new HashMap<>(3);
            result.put("success", true);
            result.put("bean", userBean);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponseWrapper.commonResponse(false, -1, "网络中断，请稍后重试！");
        }
    }


    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    @SwitchValidate("hex_webmall_status")
    public
    @ResponseBody
    Map<String, Object> signIn() throws Exception {
        try {
            return service.signIn(ActionUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponseWrapper.commonResponse(false, -1, "签到失败，请稍后重试！");
        }
    }

    /**
     * 抢激活码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    @SwitchValidate("hex_webmall_status")
    public
    @ResponseBody
    Map<String, Object> exchange() throws Exception {
        try {
            return service.exchange(ActionUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponseWrapper.commonResponse(false, -1, "兑换失败，请稍后重试！");
        }
    }


    @RequestMapping(value = "/serverlist", method = RequestMethod.GET)
    public
    @ResponseBody
    String  serverlist(int serverid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String re = "{\"data\":[{\"roleId\":\"4307\",\"roleLevel\":\"71\",\"roleName\":\"S16杀毒用瑞星\",\"serverId\":\"16\",\"serverName\":\"双线16区\"},{\"roleId\":\"8135\",\"roleLevel\":\"48\",\"roleName\":\"S16.我是小号\",\"serverId\":\"16\",\"serverName\":\"双线16区\"}],\"code\":\"0\",\"success\":\"true\",\"msg\":\"成功\"}" ;
        ObjectNode objectNode = objectMapper.readValue(re,ObjectNode.class);
        return objectNode.findPath("data").toString();
    }
}
