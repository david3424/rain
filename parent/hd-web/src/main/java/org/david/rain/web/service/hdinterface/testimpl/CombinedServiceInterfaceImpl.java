package org.david.rain.web.service.hdinterface.testimpl;

import common.HdUser;
import org.david.rain.web.service.hdinterface.CombinedServiceInterface;

import java.io.IOException;

/**
 * Created by IDEA.
 * User: 沈凡
 * Date: 13-12-26
 */
public class CombinedServiceInterfaceImpl implements CombinedServiceInterface {

    @Override
    public String searchServerName(HdUser hdUser) throws IOException {
        return "测试状态测试服";
    }
}
