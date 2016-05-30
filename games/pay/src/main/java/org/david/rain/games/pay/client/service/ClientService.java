package org.david.rain.games.pay.client.service;

import org.david.rain.games.pay.client.dao.dbutils.CommonList;
import org.david.rain.games.pay.client.dao.dbutils.search.Search;
import org.david.rain.games.pay.client.dao.entity.OpayDic;
import org.david.rain.games.pay.client.dao.Idao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * User: david
 * Date: 14-7-4
 * Time: 下午5:22
 */

@Service
public class ClientService {


    @Autowired
    private Idao wdao;

    public static final Logger LOG = LoggerFactory.getLogger(ClientService.class);


    public CommonList pagination(Search search, Class<OpayDic> taskClass) throws Exception {

        return wdao.pagination(search, taskClass);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveClient(OpayDic entity) throws SQLException {
        if (entity.getId() == null) {
            wdao.insert(entity);
        } else {
            wdao.updateBean(entity);
        }
    }

    public void deleteClient(int id) {
        try {
            wdao.update("delete from o_pay_dic where id  = ? ", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OpayDic getClient(int id) {
        try {
            return wdao.queryObject(OpayDic.class, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer genAppid() {
        try {
            Integer maxid = wdao.queryScalar("select max(id) from o_pay_dic ");
            return maxid == null ? 1000 : maxid + 1000;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public OpayDic getClientByAppid(String appid) {
        try {
//            LOG.debug("getClientByAppid by appid [{}]",appid);
            return wdao.queryObject(OpayDic.class, "select * from o_pay_dic where appid = ? ", appid);
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("getClientByAppid excetion [{}]", appid);
            return null;
        }
    }
}
