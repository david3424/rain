package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.david.rain.wmeovg.callback.servlet.ICallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Created by mac on 16/4/12.
 * *
 */
public class SendPrizeCallbackService implements ICallbackService {
    Logger logger = LoggerFactory.getLogger(SendPrizeCallbackService.class);

    AwardPrizesService awardPrizesService;

    @Override
    public void receive(String gid, String cid, String appid, int status, int count, String callback) throws Exception {
        logger.info("gid:{},cid:{},appid:{},callback:{},status:{}", gid, cid, appid, callback, status);
        String[] params = callback.split("&");
        String tbName = params[0];
        logger.info("{}", awardPrizesService);
//        String dataSource = params[3];
//        String note = params[2];
       /* if (note.equals("ROLEID")) {//新roleid发奖
            //id 有可能不为int 需检查
            int id = Integer.parseInt(params[1]);
            prizeTableBean = awardPrizesService.gainPrizeBean(tbName, id);
            if (status == 0) {
                // 发送成功
                if (isCoupon) {
                    awardPrizesService.updateStatus(id, 1, DateUtils.getCurrentFormatDateTime());
                } else {
                    awardPrizesService.updateStatus(id, 1);
                }

            } else {
                awardPrizesService.updateStatus(id, 3);
            }

            awardPrizesService.addLog(prizeTableBean.getUsername(), prizeTableBean.getServer(),
                    Long.parseLong(prizeTableBean.getRoleid()), prizeTableBean.getPrize(), status, id, 1, gid);
        }*/
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        if (awardPrizesService == null) {
            awardPrizesService = SpringContextSupport.getSpringBean(AwardPrizesService.class);
        }
    }
}