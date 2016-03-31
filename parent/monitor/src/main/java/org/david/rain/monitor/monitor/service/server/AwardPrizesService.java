package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.dbutils.repository.impl.CommonDao;
import org.david.rain.monitor.monitor.util.DataSourceContext;
import org.david.rain.service.logservice.OperationLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwardPrizesService {

    @Autowired
    OperationLogger operationLogger;

    private static Logger logger = LoggerFactory.getLogger(AwardPrizesService.class);

    private CommonDao commonDao;

    @Autowired
    DataSourceContext dataSourceContext;

    public AwardPrizesService(String dataSource) {
        this.commonDao = new CommonDao(dataSourceContext.getDataSource(dataSource));
    }

    public AwardPrizesService() {
    }

    /**
     * 定时器触发发奖过程
     * status = 0：开始 1:结束  2:锁定  3:错误  -10: 奖品ID异常(停止发放) 5:重复 8：申请成功，等待回调 4：申请失败
     * log存在两条相同记录，停止发放
     */
    public void execute() {
        logger.info("定时器进入发奖逻辑bean");
        logger.info("xxx:{}", commonDao);
    }

}