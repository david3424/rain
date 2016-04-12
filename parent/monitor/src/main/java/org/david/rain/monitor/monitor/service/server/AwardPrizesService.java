package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.dbutils.repository.impl.CommonDao;
import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.persistence.SendPrizeMapper;
import org.david.rain.monitor.monitor.util.DataSourceContext;
import org.david.rain.monitor.monitor.vo.PrizeBean;
import org.david.rain.service.logservice.OperationLogger;
import org.david.rain.wmeovg.request.service.PrizeServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AwardPrizesService {

    @Autowired
    OperationLogger operationLogger;

    private static Logger logger = LoggerFactory.getLogger(AwardPrizesService.class);

    @Autowired
    DataSourceContext dataSourceContext;

    @Autowired
    SendPrizeMapper sendPrizeMapper;

    private CommonDao commonDao;
    private SendPrize sendPrize;

    /**
     * 定时器触发发奖过程
     * status = 0：开始 1:结束  2:锁定  3:错误  -10: 奖品ID异常(停止发放) 5:重复 8：申请成功，等待回调 4：申请失败
     * log存在两条相同记录，停止发放
     */
    public void execute(SendPrize sendPrize) {
        this.commonDao = dataSourceContext.getCommonDao(sendPrize.getDatasource());
        this.sendPrize = sendPrize;
        logger.info("定时器进入发奖逻辑bean");
        try {
            if (!is_open()) {
                logger.debug("总开关关闭，临时维护");
                return;
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            logger.error("exception:{}", e.getMessage());
            return;
        }
        try {
            List<PrizeBean> list = this.getRecordsByCount();
            for (PrizeBean p : list) {
//                logger.info("{},{},{},{}", p.getUserName(), p.getRoleId(), p.getServer(), p.getId());
                int rr = PrizeServiceManager.prizeService.send(PrizeServiceManager.prizeService.genGid(),
                        p.getServer(), p.getUserName(), sendPrize.getTableName(), p.getRoleId(), p.getPrize(), "callback&" + p.getId());
                logger.info("=====result statusCode of sendprize:{}", rr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<PrizeBean> getRecordsByCount() throws SQLException {

        return commonDao.queryObjects(PrizeBean.class, "select * from " + sendPrize.getTableName() + " where status = 0 limit 1 ");
    }

    private boolean is_open() throws SQLException {
        SendPrize sendPrize = new SendPrize();
        sendPrize.setTableName(this.sendPrize.getTableName());
        sendPrize.setStatus(1);
        return sendPrizeMapper.selectCount(sendPrize) > 0;
//        return (Integer) commonDao.queryScalar("select count(1) from send_prize_properties where status = 1 and tablename = ? ", sendPrize.getTableName()) > 0;
    }

}