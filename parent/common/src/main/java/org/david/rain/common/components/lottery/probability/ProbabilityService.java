package org.david.rain.common.components.lottery.probability;

import org.david.rain.common.repository.Idao;
import org.david.rain.common.util.DateUtils;
import org.springframework.cache.annotation.Cacheable;

import java.sql.SQLException;
import java.util.List;

/**
 */
public class ProbabilityService  implements ProbabilityInteface{
    //抽奖活动基本信息表
    public final static String TABLE_BASE_INFO = "hd_lottery_baseinfo";
    //抽奖活动奖品信息表
    public final static String TABLE_PRIZES_INFO = "hd_lottery_prizesinfo";

    //根据奖品表查询基本信息
    public final static String SQL_SELECT_BASEINFO = "select * from " + TABLE_BASE_INFO + " where prizetable=?";
    //根据奖品表查询奖品概率配置信息
    public final static String SQL_SELECT_LOTTERY_PRIZES = "select id,outputid as pageid, prizename,prizetable,prizeid as prize ," +
            "totallimit," +
            " steplimit,todaylimit,probability,description,defaultstatus from " + TABLE_PRIZES_INFO + " where prizetable=?"; //TODO 字段名要对应

    //由具体的项目注入
    private Idao dao;

    public ProbabilityService()
    {}

    //构造注入
    public ProbabilityService(Idao dao) {
        this.dao = dao;
    }

    @Cacheable(value = "cache3sec",key="'probability_lottery_prizeinfo_' + #prizetable")
    public List<ProbabilityLotteryPrize> prizeInfoConfig(String prizetable) throws Exception {
        return dao.queryObjects(ProbabilityLotteryPrize.class, SQL_SELECT_LOTTERY_PRIZES, prizetable);
    }

    @Cacheable(value = "cache3sec", key="'probability_lottery_accuracy_' + #prizetable")
    public int lotteryAccuracy(String prizetable) throws SQLException {
        return baseInfoConfig(prizetable).getAccuracy();
    }

    private LotteryBaseInfo baseInfoConfig(String prizetable) throws SQLException {
        return dao.queryObject(LotteryBaseInfo.class, SQL_SELECT_BASEINFO, prizetable);
    }


    /**
     * 今日发送数量，如果出现异常，返回最大整数Integer.MAX_VALUE;
     *
     * @param prize
     * @return
     */
    public int getTodayPrizeSendedCount(ProbabilityLotteryPrize prize) {
        String sql = "select count(1) from " + prize.getPrizeTable() + " where prize=? and date=?";
        try {
            Long count = dao.queryCount(sql, prize.getPrize(), DateUtils.getCurrentFormatDate());
            return count.intValue();
        } catch (SQLException e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 总共已发送数量 ，如果出现异常，返回最大整数Integer.MAX_VALUE;
     *
     * @param prize
     * @return
     */
    public int getPrizeSendedCount(ProbabilityLotteryPrize prize){
        String sql = "select count(1) from " + prize.getPrizeTable() + " where prize=? ";
        try {
            Long count = dao.queryCount(sql, prize.getPrize());
            return count.intValue();
        } catch (SQLException e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

}
