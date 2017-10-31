package org.david.rain.cloud.start.service;

import org.david.rain.cloud.start.dao.mapper.king.KoTaskMapper;
import org.david.rain.cloud.start.pojo.KoTask;
import org.david.rain.common.logback.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xdw9486
 */
@Service
public class TransactionTestService {

    @Autowired
    KoTaskMapper koTaskMapper;

    @Transactional(rollbackFor = Exception.class)
    public void updateAndSave() {

        KoTask koTask = koTaskMapper.selectByPrimaryKey(1);
        koTask.setRemark("modified task 1 info ");
        koTaskMapper.updateByPrimaryKey(koTask);
        koTask = koTaskMapper.selectByPrimaryKey(1);
        LoggerUtil.info("task 1 info : {}", koTask);

        KoTask koTaskNew = new KoTask();
        koTaskNew.setRemark("new task test for transaction ");
        koTaskMapper.insert(koTaskNew);
    }


}
