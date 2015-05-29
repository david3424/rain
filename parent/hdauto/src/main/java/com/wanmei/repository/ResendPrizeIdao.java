package com.david.web.wanmei.repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: davidxu
 * Date: 13-1-10
 * Time: 下午2:45
 */

public interface ResendPrizeIdao extends Idao{

    List queryGroupByTablename(String tablename);

    List queryFailedGid(String tablename);
}
