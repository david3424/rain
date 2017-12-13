package org.david.rain.boot.start.service.crm;

import org.apache.commons.collections.map.HashedMap;
import org.david.rain.boot.start.dao.mapper.king.KoTaskMapper;
import org.david.rain.boot.start.dao.mapper.oracle.PayFundMapper;
import org.david.rain.boot.start.pojo.KoTask;
import org.david.rain.common.exception.ErrorCode;
import org.david.rain.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 双写service
 */
@Transactional(transactionManager = "transactionManager")
public class CreateAccountModifyServiceBoth implements IAccountModify {

    @Autowired
    KoTaskMapper newCrmMapper;

    @Autowired
    PayFundMapper oldCrmMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> createAccount(KoTask mobileDto, String encrypt_mobile) {

//        先操作老CRM，匹配所需字段
        int oldAccountId = oldCrmMapper.insert(mobileDto);

        if (oldAccountId <= 0) {
//           失败，直接回滚当前事务
            throw new ServiceException(ErrorCode.SERVICE_EXCEPTION, "插入老CRM-ACCOUNT异常");
        }
        //保存
        mobileDto.setAccountId(oldAccountId);
//        把新 accountId插入新CRM表中，方便排查问题用
        newCrmMapper.insert(mobileDto);
        return new HashedMap();

    }
}
