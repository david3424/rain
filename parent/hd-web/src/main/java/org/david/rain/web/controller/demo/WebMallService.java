package org.david.rain.web.controller.demo;

import org.apache.commons.lang.StringUtils;
import org.david.rain.common.components.util.ActionUtil;
import org.david.rain.common.lang.CommonList;
import org.david.rain.common.lang.Tuple;
import org.david.rain.common.repository.Idao;
import org.david.rain.common.search.Search;
import org.david.rain.common.util.DateUtils;
import org.david.rain.common.util.ObjectResponseWrapper;
import org.david.rain.common.util.memcached.MemcachedManager;
import org.david.rain.web.service.hdinterface.entity.ScoreInfoBean;
import org.david.rain.web.service.hdinterface.entity.UserBean;
import org.david.rain.web.service.webservice.ServiceManager;
import org.david.rain.web.util.customannotation.TransactionalForExceptionRollback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.david.rain.web.controller.demo.WebMallConst.*;
/**
 */
@Service
public class WebMallService {


    private static Logger logger = LoggerFactory.getLogger(WebMallService.class);


    @Autowired
    MemcachedManager memcachedManager;

    @Autowired
    @Qualifier("writeDaoImp")
    private Idao dao;

    @Autowired
    @Qualifier("daoImp")
    private Idao readDao;


    @Autowired
    ServiceManager serviceManager;


    /**
     * 提交服务器角色信息保存
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public Map<String, Object> submit(UserBean bean) throws Exception {

        try {
            bean.setCreatetime(DateUtils.getCurrentFormatDateTime());
            bean.setUsername(ActionUtil.getUserName());
            bean.setIp(ActionUtil.getRealIp());
            bean.setStatus(0);
            int rtn = serviceManager.hdIscoreServiceInterface.updateZUser(bean);
            String msg = rtn > 0 ? "修改成功！" : "修改失败！";
            return ObjectResponseWrapper.commonResponse(rtn > 0, rtn, msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponseWrapper.commonResponse(false, -1, "信息保存失败，请稍后重试。");
        }
    }


    /**
     * 签到逻辑
     *
     * @param username x
     * @return
     * @throws Exception
     */
    @TransactionalForExceptionRollback()
    public Map<String, Object> signIn(String username) throws Exception {

        String date = DateUtils.getCurrentFormatDate();
        String sql = "select count(1)  from " + TABLE_SIGN_IN_LOG + " where username=? and date=?";
        long id = readDao.queryCount(sql, username, date);
        if (id > 0) {
            return ObjectResponseWrapper.commonResponse(false, -1, "您今天已经签到了哦，请明天再来吧。");
        } else {
            dao.insert(new SignInLog(username, date));
        }
        int score = WebMallConst.TIP_SCORE_MAP.get("sign");//MAP存有对应关系
        int result = updateScoreAndSaveLog(username, WebMallConst.TIP_HDNAME_MAP.get("sign"), score, ActionUtil.getRealIp());
        if (result == 0) {
            return ObjectResponseWrapper.commonResponse(true, score, "签到成功，恭喜您获得" + score + "个魔石！");
        } else {
            throw new Exception("签到保存并更新积分失败，返回原因：" + result); //事务声明上，会回滚之前的签到记录
        }
    }


    /**
     * 百科答题逻辑
     *
     * @param answer
     * @return
     * @throws Exception
     */
    @TransactionalForExceptionRollback()
    public Map<String, Object> answer(Answer answer) throws Exception {
        String username = ActionUtil.getUserName();
        String sql = "select id  from " + TABLE_WEBMALL_ANSWER + " where username=? ";
        Integer id = readDao.queryScalar(sql, username);
        Tuple<Boolean,String> answerResult=isRight(answer);
        if (id != null && id > 0) {
            return ObjectResponseWrapper.commonResponse(false, -1, "亲，您已经答题了哦。");
        } else if (!answerResult.l) {
            return ObjectResponseWrapper.commonResponse(false, -1, answerResult.r);
        } else {
            sql = "insert into " + TABLE_WEBMALL_ANSWER + "(username,createtime,ip,status)values(?,?,?,?)";
            dao.update(sql, username, DateUtils.getCurrentFormatDateTime(), ActionUtil.getRealIp(), 0);
        }

        int score = WebMallConst.TIP_SCORE_MAP.get("answer");
        int result = updateScoreAndSaveLog(username, WebMallConst.TIP_HDNAME_MAP.get("answer"), score, ActionUtil.getRealIp());
        if (result == 0) {
            return ObjectResponseWrapper.commonResponse(true, score, "HEX百科答题正确，恭喜您获得" + score + "个魔石！");
        } else {
            throw new Exception("HEX百科答题更新魔石失败，返回原因：" + result);
        }
    }

    /**
     * 判断答题是否正确 太懒了 。。
     *
     * @param answerInfo
     * @return
     */
    public Tuple<Boolean,String> isRight(Answer answerInfo) {
        String errorIndex="";
        if(!answers[0].equals(answerInfo.getA0())){
            errorIndex+="1、";
        }
        if(!answers[1].equals(answerInfo.getA1())){
            errorIndex+="2、";
        }
        if(!answers[2].equals(answerInfo.getA2())){
            errorIndex+="3、";
        }
        if(!answers[3].equals(answerInfo.getA3())){
            errorIndex+="4、";
        }
        if(!answers[4].equals(answerInfo.getA4())){
            errorIndex+="5、";
        }
        if(!answers[5].equals(answerInfo.getA5())){
            errorIndex+="6、";
        }
        if(!answers[6].equals(answerInfo.getA6())){
            errorIndex+="7、";
        }
        if(!answers[7].equals(answerInfo.getA7())){
            errorIndex+="8、";
        }
        if(StringUtils.isBlank(errorIndex)){
            return new Tuple<>(true,"");
        }else{
            return new Tuple<>(false,"第"+errorIndex.substring(0,errorIndex.length()-1)+"题回答错误！");
        }
    }


    /**
     * 兑换激活码
     *
     * @param username
     * @return
     * @throws Exception
     */
    @TransactionalForExceptionRollback()
    public Map<String, Object> exchange(String username) throws Exception {

        try {
            if (!memcachedManager.add(username + TABLE_CODE, 1)) {
                return ObjectResponseWrapper.commonResponse(false, -1, "操作太频繁。");
            }
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);//小时
            int phase = 0;
            if (hour < 10) {
                return ObjectResponseWrapper.commonResponse(false, -1, "亲，抢码在10点开启，敬请期待吧。");
            } else if (hour < 12) {
                phase = 1;
            } else if (hour >= 12 && hour < 14) {
                return ObjectResponseWrapper.commonResponse(false, -1, "亲，下次抢码在14点开启，敬请期待吧。");
            } else if (hour < 16) {
                phase = 2;
            } else if (hour >= 16 && hour < 19) {
                return ObjectResponseWrapper.commonResponse(false, -1, "亲，下次抢码在19点开启，敬请期待吧。");
            } else if (phase < 21) {
                phase = 3;
            } else if (hour >= 21) {
                return ObjectResponseWrapper.commonResponse(false, -1, "亲，整点抢码时间已过，等待下一次抢号吧。");
            }
            if (phase == 0) {
                throw new Exception("phase==0");
            }
            String sql = "select * from " + TABLE_CODE_DAYLIMIT + " where phase=?";
            CardLimit limitObject = dao.queryObject(CardLimit.class, sql, phase);
            sql = "select total from " + TABLE_CODE_OBTAIN_STATICS + " where date=? and phase=?";
            String date = DateUtils.getCurrentFormatDate();
            Integer count = readDao.queryScalar(sql, date, phase);
            if (limitObject.getRestrictnum() == (count == null ? 0 : count.intValue())) {
                return ObjectResponseWrapper.commonResponse(false, -1, "该时段码已抢完，等下一个时段吧。");
            }
            UserBean userBean = getUserBean(username);
            if (userBean == null) {
                return ObjectResponseWrapper.commonResponse(false, -1, "系统错误，请稍后重试。");
            }
            if (userBean.getTotalscore() - userBean.getUsedscore() < WebMallConst.EXCHANGE_CODE_NEED_STONE) {
                return ObjectResponseWrapper.commonResponse(false, -1, "亲，您魔石数量不足哦，赶紧获取魔石吧。");
            }
            sql = "select id  from  " + TABLE_CODE + "  where username=?";
            Integer id = dao.queryScalar(sql, username);
            if (id != null && id > 0) {
                return ObjectResponseWrapper.commonResponse(false, -1, "每个账号只能兑换一个哦。");
            }

            sql = "select id , code  from  " + TABLE_CODE + "  where  status = 0 limit 1 for update";
            String updateSql = "update " + TABLE_CODE + " set username=?,status = 1,createtime=?,ip=? where id = ? and status=0 ";
//            String logSql = "INSERT INTO " + TABLE_CODE_OBTAIN_STATICS + " (date,phase,total,status) values (?,?,?,?) ON DUPLICATE KEY" +
//                    " UPDATE total=total+1 where total<?";
            Card cardInfo = dao.queryObject(Card.class, sql);
            if (cardInfo == null || StringUtils.isBlank(cardInfo.getCode())) {
                return ObjectResponseWrapper.commonResponse(false, -1, "亲，激活码暂时已发完。");
            }
            sql = "select id from " + TABLE_CODE_OBTAIN_STATICS + " where date =? and phase=?";
            id = dao.queryScalar(sql, date, phase);
            if (id == null) {
                sql = "INSERT INTO " + TABLE_CODE_OBTAIN_STATICS + " (date,phase,total,status) values (?,?,?,?)";
                dao.update(sql, date, phase, 1, 0);
            } else {
                sql = "update " + TABLE_CODE_OBTAIN_STATICS + " set total=total+1 where total<?";
                int rtn = dao.update(sql, limitObject.getRestrictnum());
                if (rtn <= 0) {
                    return ObjectResponseWrapper.commonResponse(false, -1, "亲，该时段激活码暂时已发完。");
                }
            }
            int rtn = dao.update(updateSql, username, DateUtils.getCurrentFormatDateTime(), ActionUtil.getRealIp(), cardInfo.getId());
            if (rtn <= 0) {
                throw new Exception("兑换激活码 updateSql失败：rtn1*rtn " + rtn);
            }
            int result = updateScoreAndSaveLog(username, "兑换激活码", -WebMallConst.EXCHANGE_CODE_NEED_STONE, ActionUtil.getRealIp());
            if (result == 0) {
                return ObjectResponseWrapper.commonResponse(true, 1, cardInfo.getCode());
            } else {
                throw new Exception("兑换激活码 消耗积分失败 返回：" + result);
            }
        } finally {
            memcachedManager.delete(username + TABLE_CODE);
        }

    }


    /**
     * 得到这个用户的签到日志
     * *
     */
    public List<String> getSignLogs(String username) throws SQLException {
        return dao.queryOneColumnList("select date from " + TABLE_SIGN_IN_LOG + " where username=?", username);
    }

    /**
     * 得到这个用户的签到日志
     * *
     */
    public boolean isSignToday(String username, String date) throws SQLException {
        return dao.queryCount("select count(1) from " + TABLE_SIGN_IN_LOG + " where username=? and date=?", username, date) > 0 ? true : false;

    }

    public boolean isWechatAttention(String username) throws SQLException {
        return dao.queryCount("select count(1) from " + TABLE_WEIXIN_ATTENTION_LOG + " where username=?", username) > 0 ? true : false;

    }

    public boolean isAnswer(String username) throws SQLException {
        return dao.queryCount("select count(1) from " + TABLE_WEBMALL_ANSWER + " where username=?", username) > 0 ? true : false;

    }

    public boolean isDownload(String username) throws SQLException {
        return dao.queryCount("select count(1) from " + TABLE_WEIXIN_ATTENTION_LOG + " where username=?", username) > 0 ? true : false;

    }

    public UserBean getUserBean(String username) {
        return serviceManager.hdIscoreServiceInterface.getUserBean(username);
    }

    public String getCode(String username) throws SQLException {
        return dao.queryScalar("select code from " + TABLE_CODE + " where username=?", username);
    }

    /**
     * 插入log操作  hdname:获取积分的名称活动   score：Z点积分值  正值是得到积分  负值是消耗积分
     * 0 正常 -1 参数异常，-2 不在白名单，-3 不在活动时间中，-4 超过单个最大值，-5 保存log系统错误，-6 查询用户积分系统错误，-7 数据异常 积分为负
     */
    public int updateScoreAndSaveLog(String username, String hdname, Integer score, String ip) throws Exception {
        Long orderId = serviceManager.hdIscoreServiceInterface.createOrderId(22);
        int result = serviceManager.hdIscoreServiceInterface.consumeZScore(username, score > 0 ? 1 : -1, orderId, score, hdname, ip); //接口增减记录
        if (result != 0) {
            dao.insert(new FailLog(username, hdname + "失败，返回：" + result)); //失败日志
        }
        return result;
    }


    public CommonList<ScoreInfoBean> queryStoneDetailList(Integer pageNo, Integer pageSize, Integer type, String username) throws Exception {


        Search search = new Search();
        search.addSelectSql("SELECT * FROM " + TABLE_WEBMALL_USERSCORE_LOG);
        search.addSelectCountSql("SELECT count(1) FROM " + TABLE_WEBMALL_USERSCORE_LOG);
        search.addWhere(Search.SEARCH_AND, " username=? ", username);
        search.addWhere(Search.SEARCH_AND, " score>0 ");
        search.addWhere(Search.SEARCH_AND, " status != -1 ");
        search.setPageSize(pageSize);
        search.setPageNo(pageNo);
        search.addOrder("createtime", "desc");
        CommonList commonList = null;
        commonList = serviceManager.hdIscoreServiceInterface.getTotalZLogByUser(pageNo,pageSize,username);
        return commonList;
    }


}
