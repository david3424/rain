package com.david.web.wanmei.extend.hdswitch.remote.impl;

import com.caucho.services.server.ServiceContext;
import com.david.web.wanmei.entity.SendProperty;
import com.david.web.wanmei.extend.hdswitch.remote.SendSwitchInterface;
import com.david.web.wanmei.service.task.SendPropertyService;
import com.david.web.wanmei.util.PaginationCList;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by czw on 13-12-16.
 */
public class SwitchService implements SendSwitchInterface {


    public SwitchService() {
    }



    private SendPropertyService getSendPropertyService()
    {
        HttpServletRequest request = (HttpServletRequest)ServiceContext.getContextRequest();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        return  context.getBean("sendPropertyService", SendPropertyService.class);
    }

    @Override
    public String getJobListJson(int pageSize, int pageNo, String tableName) throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new PaginationCList<SendProperty>(sendPropertyService.getAllSendProperty(pageSize, pageNo, tableName)));
    }

    public int closeSendPrize(String prizeTable, String datasource) throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        return sendPropertyService.closeSwitch(prizeTable, datasource);
    }

    @Override
    public int closeSendPrize(String prizeTable) throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        return sendPropertyService.closeSwitch(prizeTable);
    }

    public int openSendPrize(String prizeTable, String datasource) throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        return sendPropertyService.openSwitch(prizeTable, datasource);
    }

    @Override
    public int openSendPrize(String prizeTable) throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        return sendPropertyService.openSwitch(prizeTable);
    }

    @Override
    public int closeAll() throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        return sendPropertyService.closeAll();
    }

    @Override
    public int openAll() throws Exception {
        SendPropertyService sendPropertyService = getSendPropertyService();
        return sendPropertyService.openAll();
    }
}
