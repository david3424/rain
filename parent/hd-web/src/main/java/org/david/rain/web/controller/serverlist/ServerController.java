package org.david.rain.web.controller.serverlist;

import org.david.rain.web.util.validate.TimeStatistic;
import org.david.rain.common.components.server.ServerListManager;
import org.david.rain.common.components.server.restrict.ServerBean;
import org.david.rain.common.components.server.restrict.ServerRestrictSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 列表
 * Date: 12-10-29
 * Time: 上午9:09
 */

@Controller
@RequestMapping(value = "/server/list/")
public class ServerController {

    private ServerListManager serverListManager;

    public ServerListManager getServerListManager() {
        return serverListManager;
    }

    @Autowired
    public void setServerListManager(@Qualifier("serverListManager") ServerListManager serverListManager) {
        this.serverListManager = serverListManager;
    }

    @Autowired(required = false)
    private ServerRestrictSupport serverRestrictSupport;


    @RequestMapping(value = "/xml")
    public
    @ResponseBody
    String xml() throws IOException {
        return serverListManager.getServerListXML();
    }

    @RequestMapping(value = "/json")
    @TimeStatistic
    @Cacheable(value = "cache3min",key = "'wm_switch__event_serverlist'")
    public
    @ResponseBody
    Map<String, List<Map<String, Object>>> json() throws IOException {
        return serverListManager.getServerListMap();
    }



    @RequestMapping(value = "/newserver/restrict")
    public
    @ResponseBody
    List<ServerBean> newServers(String hden) throws SQLException, IOException {
        return serverRestrictSupport.getServerBeanListForNewServerJs(hden);
    }
}
