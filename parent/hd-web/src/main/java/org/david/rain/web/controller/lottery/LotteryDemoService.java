package org.david.rain.web.controller.lottery;

import common.UserInfo;
import org.david.rain.common.components.lottery.AbstractPrize;
import org.david.rain.common.components.lottery.LotteryService;
import org.david.rain.common.components.lottery.NullLotteryPrize;
import org.david.rain.common.components.util.ActionUtil;
import org.david.rain.common.lang.Tuple;
import org.david.rain.common.repository.Idao;
import org.david.rain.common.util.DateUtils;
import org.david.rain.common.util.ObjectResponseWrapper;
import org.david.rain.common.util.memcached.MemcachedManager;
import org.david.rain.web.service.webservice.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

/**
 * this template use File | Settings | File Templates.
 */
@Service
public class LotteryDemoService {

	private static Logger logger = LoggerFactory.getLogger(LotteryDemoService.class);
	private final static String TABLE_LOTTERYTIMES = "lottery_demo_lotterytimes";
	private final static String TABLE_SIGN_LOG = "lottery_demo_signlog";
	private final static String TABLE_lOTTERY_PRIZE = "lottery_demo_prize";
	private final static String TABLE_EXCHANGE_PRIZE = "lottery_demo_exchange_prize";
	private final static String TABLE_USER = "lottery_demo_user";
	private final static Integer TYPE_PRIZE_LOTTERY = 0;
	static HashMap<String, Integer> dateMap = new HashMap<>();
	public final static HashMap<Integer, Integer> sunshinePrizes = new HashMap<>();
	public final static HashMap<Integer, String> sunshinePrizenames = new HashMap<>();
	public final static HashMap<Integer, Integer> sunshinePageIds = new HashMap<>();

	public static Tuple<String, Integer> sevenDaysPrize = new Tuple<>("签到7天礼包", 11449);
	public static Tuple<String, Integer> fourDaysPrize = new Tuple<>("签到14天礼包", 11450);



	private static String startTime = "2015-09-17 11:55:00";
	private static String endTime = "2015-10-02 18:00:00";

	static {

		sunshinePrizes.put(0, 8707);
		sunshinePrizes.put(1, 9898);
		sunshinePrizenames.put(0, "五级灵兽果");
		sunshinePrizenames.put(1, "一级奇异花");
		sunshinePageIds.put(0, 15);
		sunshinePageIds.put(1, 16);

		dateMap.put("2015-10-16", 0);
		dateMap.put("2015-10-18", 1);
		dateMap.put("2015-10-19", 2);
		dateMap.put("2015-10-20", 3);
		dateMap.put("2015-10-21", 4);
		dateMap.put("2015-10-22", 5);
		dateMap.put("2015-10-23", 6);
		dateMap.put("2015-10-24", 7);
		dateMap.put("2015-10-25", 8);
		dateMap.put("2015-10-26", 9);
		dateMap.put("2015-10-27", 10);
		dateMap.put("2015-10-28", 11);
		dateMap.put("2015-10-29", 12);
		dateMap.put("2015-10-30", 13);
		dateMap.put("2015-10-31", 14);
		dateMap.put("2015-11-01", 15);

	}

	private ServiceManager serviceManager;

	@Autowired
    MemcachedManager memcachedManager;

	@Autowired
	@Qualifier("writeDaoImp")
	private Idao dao;

	@Autowired
	@Qualifier("daoImp")
	private Idao readDao;

	/**
	 * 抽奖逻辑实现，若未中奖，两个阳光普照奖随机选一个发给玩家。
	 */

	@Autowired
	@Qualifier("probabilityLotteryService")
	private LotteryService lotteryService;

	@Autowired
	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	/**
	 * 签到逻辑 成功签到 获取一次抽奖机会
	 *
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map signIn(String username, String curDate) throws Exception {
		UserInfo user = getUserInfo(username);
		if (user == null) {
			return ObjectResponseWrapper.commonResponse(false, 0, "请先提交服务器角色信息。");
		}
		if (!dateMap.containsKey(curDate)) {
			return ObjectResponseWrapper.commonResponse(false, -1, "对不起，您的签到日期异常，请稍后重试。");
		}
		String sql = "select id from " + TABLE_SIGN_LOG + " where username=? and signcode=?";
		Integer id = dao.queryScalar(sql, username, dateMap.get(curDate));
		if (id != null && id > 0) {
			return ObjectResponseWrapper.commonResponse(false, -1, "对不起，您今天已经签到了哦，请明天再来吧。");
		} else {
			sql = "insert into " + TABLE_SIGN_LOG + "(username,date,signcode,createtime,ip,status)values(?,?,?,?,?,?)";
			dao.update(sql, username, curDate, dateMap.get(curDate), DateUtils.getCurrentFormatDateTime(),
					ActionUtil.getRealIp(), 0);
//            抽奖次数+1
			sql = "select id from " + TABLE_LOTTERYTIMES + " where username=?";
			id = dao.queryScalar(sql, username);
			if (id != null && id > 0) {
				sql = "update " + TABLE_LOTTERYTIMES + " set signtimes=signtimes+1 where username=? and signdate<?";
				if (dao.update(sql, username, curDate) <= 0) {
					throw new Exception("sign update lotterytimes fail");
				}
			} else {
				dao.insert(new LotteryTimes(username, 1, curDate));
			}
			sql = "select count(1) from " + TABLE_SIGN_LOG + " where username=?";
			Long result = dao.queryScalar(sql, username);
			logger.info("signtimes=" + result + " curDate=" + curDate);
			if (result != null && result >= 7) {
				Boolean flag = (result == 14) ;
				Integer prize = flag ? fourDaysPrize.r : sevenDaysPrize.r;
				sql = "select id from " + TABLE_EXCHANGE_PRIZE + " where username=? and prize=?";
				id = dao.queryScalar(sql, username, prize);
				if (id == null || id > 0) {//已经领取
                    sql = "insert into " + TABLE_EXCHANGE_PRIZE
                            + "(userid,username,roleid,rolename,server,servername,prize,prizename,"
                            + "type,date,createtime,ip,status)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    dao.update(sql, ActionUtil.getUserId(), username, user.getRoleId(), user.getRoleName(),
                            user.getServer(), user.getServerName(), prize,
                            flag ? fourDaysPrize.l : sevenDaysPrize.l, 0, DateUtils.getCurrentFormatDate(),
                            DateUtils.getCurrentFormatDateTime(), ActionUtil.getRealIp(), 0);
				}
			}
		}
		return ObjectResponseWrapper.commonResponse(true, 1, dateMap.get(curDate).toString());
	}

	public List getSignInCode(String username) throws SQLException {
		String sql = "select signcode from " + TABLE_SIGN_LOG + " where username=?";
		return readDao.queryOneColumnList(sql, username);
	}

	public Long getAttendCount() throws SQLException {
		String sql = "select count(1) from " + TABLE_LOTTERYTIMES;
		return readDao.queryCount(sql);
	}

	@Transactional(rollbackFor = Exception.class)
	public Map lottery() throws Exception {
		String username = ActionUtil.getUserName();
		int leftLotteryTimes = getLeftLotteryTimes(username);
		if (leftLotteryTimes > 0) {
			UserInfo userInfo = getUserInfo(username);
			if (userInfo == null) {
				throw new Exception("GOD,请提交服务器角色信息！");
			}
			AbstractPrize abstractPrize = lotteryService.lottery(TABLE_lOTTERY_PRIZE);
			LotteryPrize prize;
			if (abstractPrize instanceof NullLotteryPrize) {
				prize = getSunshinePrize(abstractPrize, username, userInfo);
			} else {
				prize = setCommonValue(abstractPrize, username, userInfo);
			}
			return savePrizeAndWriteLog(prize, abstractPrize.getPageId());

		} else {
			return ObjectResponseWrapper.commonResponse(false, -1, "对不起，您的抽奖机会已经用完了，详细请查看抽奖机会获得方式！");
		}
	}




	/**
	 * 获取阳光普照奖
	 *
	 * @param abstractPrize
	 * @param username
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public LotteryPrize getSunshinePrize(AbstractPrize abstractPrize, String username, UserInfo info) throws Exception {
		int index = new Random().nextInt(2);
		abstractPrize.setPrize(sunshinePrizes.get(index));
		abstractPrize.setPrizeName(sunshinePrizenames.get(index));
		abstractPrize.setPageId(sunshinePageIds.get(index));
		abstractPrize.setDefaultStatus(0);
		return setCommonValue(abstractPrize, username, info);
	}

	/**
	 * 活动发奖bean赋值
	 *
	 * @param abstractPrize
	 * @param username
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public  LotteryPrize setCommonValue(AbstractPrize abstractPrize, String username, UserInfo info) throws Exception {
		LotteryPrize lotteryPrize = new LotteryPrize();
		abstractPrize.shortCutSetToEventPrize(lotteryPrize);
		lotteryPrize.setUserId(ActionUtil.getUserId());
		lotteryPrize.setUsername(username);
		lotteryPrize.setServer(info.getServer());
		lotteryPrize.setServerName(info.getServerName());
		lotteryPrize.setRoleId(info.getRoleId());
		lotteryPrize.setRoleName(info.getRoleName());
		lotteryPrize.setIp(ActionUtil.getRealIp());
		lotteryPrize.setType(TYPE_PRIZE_LOTTERY);
		return lotteryPrize;
	}

	/**
	 * 保存奖品逻辑
	 *
	 * @param prize
	 * @return
	 * @throws SQLException
	 */
	public Map savePrizeAndWriteLog(LotteryPrize prize, Integer pageId) throws SQLException {
		String sql = "update " + TABLE_LOTTERYTIMES + " set consumetimes=consumetimes+1 where username=? "
				+ "and signtimes>consumetimes";
		int rtn = dao.update(sql, prize.getUsername());
		if (rtn > 0) {
			int rtn2 = dao.insert(prize);
			if (rtn * rtn2 <= 0) {
				throw new RuntimeException("网络中断,稍后重试!");
			}
			return ObjectResponseWrapper.commonResponse(true, pageId, "恭喜您中了" + prize.getPrizeName());

		}
		return ObjectResponseWrapper.commonResponse(false, -1, "对不起您点击过于频繁！");
	}

	/**
	 * 获取当前可抽奖次数
	 *
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	@Transactional
	public int getLeftLotteryTimes(String username) throws SQLException {

		String sql = "select * from " + TABLE_LOTTERYTIMES + " where username=? ";
		LotteryTimes lotteryTimes = dao.queryObject(LotteryTimes.class, sql, username);
		return lotteryTimes == null ? 0 : (lotteryTimes.getSigntimes() - lotteryTimes.getConsumetimes());
	}



	@Cacheable("cache10sec")
	@Transactional
	public UserInfo getUserInfo(String username) throws Exception {
		String sql = "select * from " + TABLE_USER + " where username=?";
		return dao.queryObject(UserInfo.class, sql, username);
	}


	public Long getSignTimes(String username) throws SQLException {
		String sql = "select count(1) from " + TABLE_SIGN_LOG + " where username=?";
		Long result = readDao.queryScalar(sql, username);
		return result == null ? 0 : result;
	}

	public String getLatestPrize(String username) throws SQLException {
		String sql = "select prizename from " + TABLE_lOTTERY_PRIZE + " where username=? order by id desc limit 1";
		return dao.queryScalar(sql, username);
	}

}
