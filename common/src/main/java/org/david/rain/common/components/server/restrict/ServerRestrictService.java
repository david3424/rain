package org.david.rain.common.components.server.restrict;

import org.david.rain.common.repository.Idao;
import org.springframework.cache.annotation.Cacheable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 13-6-4
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public class ServerRestrictService {

    private Idao dao;

    public ServerRestrictService()
    {}


    public ServerRestrictService(Idao dao)
    {
        this.dao = dao;
    }

    static final String SERVER_TABLE = "hd_server_restrict_server";


    /**
     * 这里先用本地缓存来处理，由于本地缓存注解不支持超时时间的设置，等memcache 或者ehcache编码部分支持了在修改
     *
     * @param eventName
     * @return
     * @throws java.sql.SQLException
     */
    @Cacheable(value = "cache10sec",key="'serverlist_restrict_' + #eventName")
    public List<ServerBean> queryServerList(String eventName) throws SQLException {
        String sql = "select * from " + SERVER_TABLE + " where eventname = ? and status =1";
        List<ServerBean> list = dao.queryObjects(ServerBean.class, sql, eventName);
        if (list == null) {
            list = new ArrayList<>(1);
        }
        return list;
    }


}
