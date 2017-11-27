package com.noah.crm.cloud.utils;


import com.noah.crm.cloud.apis.api.ApisErrorCode;
import com.noah.crm.cloud.apis.constant.ApisConstants;
import com.noah.crm.cloud.apis.exception.ServiceException;

/**
 * @author xdw9486
 */
public class CustomPreconditions {

    public static void assertNotGreaterThanMaxQueryBatchSize(int size) {
        if (size > ApisConstants.MAX_BATCH_QUERY_SIZE) {
            throw new ServiceException(ApisErrorCode.BAD_REQUEST,
                    "一次查询的id数量不能超过" + ApisConstants.MAX_BATCH_QUERY_SIZE);
        }

    }


}
