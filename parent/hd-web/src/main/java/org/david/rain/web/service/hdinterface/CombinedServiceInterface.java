package org.david.rain.web.service.hdinterface;

import common.HdUser;

import java.io.IOException;

/**
 * Created by IDEA.
 * User: 沈凡
 * Date: 13-12-26
 */
public interface CombinedServiceInterface {

    /**
     * 查询合服后的服务器名字
     *
     * @param hdUser aid zoneid
     * @return 服务器名称
     */
    public String searchServerName(HdUser hdUser) throws IOException;
}
