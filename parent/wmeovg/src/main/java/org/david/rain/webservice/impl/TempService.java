package org.david.rain.webservice.impl;

import org.david.rain.webservice.ServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mac on 16/4/12.
 * *
 */
public class TempService implements ServiceInterface {

    Logger logger = LoggerFactory.getLogger(TempService.class);

    @Override
    public int sendEmailToRole(String username, int zoneid, long roleid, int prizeid, String emailTile, String emailText) {
        logger.info("发送邮件：{}，{}，结果为-1", emailTile, emailTile);
        return -1; //测试网络错误
    }

    @Override
    public int verifyRole(String username, Integer zoneid, long roleid) {
        return 0;
    }
}
