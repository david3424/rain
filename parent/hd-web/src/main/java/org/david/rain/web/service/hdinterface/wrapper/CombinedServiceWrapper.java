package org.david.rain.web.service.hdinterface.wrapper;

import common.HdUser;
import org.apache.commons.lang3.StringUtils;
import org.david.rain.web.service.hdinterface.CombinedServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IDEA.
 * Date: 14-3-3
 */
@Component
public class CombinedServiceWrapper {

    /**
     * 合服服务器名查询相关接口
     */
    public CombinedServiceInterface combinedServiceInterface;


    @Autowired
    public void setCombinedServiceInterface(CombinedServiceInterface combinedServiceInterface) {
        this.combinedServiceInterface = combinedServiceInterface;
    }

    HttpServletRequest request;


    /**
     * 查询合服后的服务器名字
     *
     * @param hdUser aid zoneid
     * @return 服务器名称
     */
    public String searchServerName(HdUser hdUser) throws IOException {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getSession().getAttribute("EVENT_INTERFACE_GAMEID") != null) {
            String gameid = request.getSession().getAttribute("EVENT_INTERFACE_GAMEID").toString();
            if (StringUtils.isNumeric(gameid))
                hdUser.setGameid(Integer.parseInt(gameid));
        }
        if (request.getSession().getAttribute("EVENT_INTERFACE_HDID") != null) {
            hdUser.setHdid(request.getSession().getAttribute("EVENT_INTERFACE_HDID").toString());
        }
        return combinedServiceInterface.searchServerName(hdUser);
    }
}
