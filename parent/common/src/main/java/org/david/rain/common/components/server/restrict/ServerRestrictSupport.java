package org.david.rain.common.components.server.restrict;


import org.david.rain.common.util.DateUtils;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by david on 13-12-4.
 *
 */
public class ServerRestrictSupport {

    ServerRestrictService serverRestrictService;

    public ServerRestrictSupport() {
    }

    public ServerRestrictSupport(ServerRestrictService serverRestrictService) {
        this.serverRestrictService = serverRestrictService;
    }

    public Map<Integer, ServerBean> getServerMap(String eventName) throws SQLException {
        List<ServerBean> list = serverRestrictService.queryServerList(eventName);
        Map<Integer, ServerBean> serverMap = new HashMap<Integer, ServerBean>();
        for (ServerBean server : list) {
            serverMap.put(server.getServer(), server);
        }
        return serverMap;
    }

    public List<ServerBean> getServerList(String eventName) throws SQLException {
        return serverRestrictService.queryServerList(eventName);
    }


    @Deprecated
    public String getServerIdsForPage(String eventName) throws SQLException {
        String re = "";
        Iterator<Integer> iditr = getServerMap(eventName).keySet().iterator();
        List<Integer> ids = new ArrayList<>();
        while (iditr.hasNext()) {
            ids.add(iditr.next());
        }
        for (int i = 0; i < ids.size(); i++) {
            if (i != ids.size() - 1) {
                re = re + ids.get(i) + ",";
            } else {
                re = re + ids.get(i);
            }
        }
        return re;
    }

    public List<ServerBean> getServerBeanListForNewServerJs(String eventName) throws SQLException {
        return getServerList(eventName);
    }

    public ServerBean getServerBean(Integer server, String eventName) throws SQLException {
        return getServerMap(eventName).get(server);
    }

    public boolean isServerValidate(Integer server, String eventName) throws SQLException {
        return getServerMap(eventName).containsKey(server);
    }

    public static boolean isInEventTime(ServerBean serverBean) throws SQLException {
        long now = System.currentTimeMillis();
        long eventStart = DateUtils.parseStrToDateTime(serverBean.getEventStart()).getTime();
        long eventEnd = DateUtils.parseStrToDateTime(serverBean.getEventEnd()).getTime();
        return now >= eventStart && now <= eventEnd;
    }

    public static boolean isInTimeTwo(ServerBean serverBean) throws SQLException {
        long now = System.currentTimeMillis();
        long eventStart = DateUtils.parseStrToDateTime(serverBean.getTwoStart()).getTime();
        long eventEnd = DateUtils.parseStrToDateTime(serverBean.getTwoEnd()).getTime();
        return now >= eventStart && now <= eventEnd;
    }

    public static boolean isInTimeOne(ServerBean serverBean) throws SQLException {
        long now = System.currentTimeMillis();
        long eventStart = DateUtils.parseStrToDateTime(serverBean.getOneStart()).getTime();
        long eventEnd = DateUtils.parseStrToDateTime(serverBean.getOneEnd()).getTime();
        return now >= eventStart && now <= eventEnd;
    }

    public static boolean isInTimeThree(ServerBean serverBean) throws SQLException {
        long now = System.currentTimeMillis();
        long eventStart = DateUtils.parseStrToDateTime(serverBean.getThreeStart()).getTime();
        long eventEnd = DateUtils.parseStrToDateTime(serverBean.getThreeEnd()).getTime();
        return now >= eventStart && now <= eventEnd;
    }
}
