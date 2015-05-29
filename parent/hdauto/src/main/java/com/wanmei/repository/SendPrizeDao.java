package com.david.web.wanmei.repository;

import com.david.web.wanmei.common.CommonList;
import com.david.web.wanmei.common.search.Search;
import com.david.web.wanmei.entity.SendProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-6-20
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */

@Qualifier("sendPrizeDao")
@Repository
public class SendPrizeDao {

    private Idao idao;


    @Autowired
    public void setIdao(@Qualifier("hdManagerDao") Idao idao) {
        this.idao = idao;
    }

    public void save(SendProperty sendProperty) throws SQLException {
        idao.insert(sendProperty);
    }

    public int delete(String tableName, String dataSource) throws SQLException {
        String sql = "delete from event_send_prize_properties where table_name = ? and datasource = ?";
        return idao.update(sql, tableName, dataSource);
    }

    public SendProperty querySendProperty(String tableName, String dataSource) throws SQLException {
        return idao.queryObject(SendProperty.class, "select * from event_send_prize_properties where table_name = ? and datasource = ?", tableName, dataSource);
    }

    public int queryMainSwitch() throws SQLException {
        String sql = "select status from event_send_prize_properties limit 1";
        Integer status = idao.queryScalar(sql);
        return status;
    }

    private int updateMainSwitch(int beforeStatus, int afterStatus) throws SQLException {
        String sql = "update event_send_prize_properties set status = ? where status = ?";
        return idao.update(sql, afterStatus, beforeStatus);
    }

    public int closeSwitch(String prizeTable) throws SQLException {
        String sql = "update event_send_prize_properties set status = ? where status = ? and table_name = ?";
        return idao.update(sql, SendProperty.Status.CLOSE.value, SendProperty.Status.OPEN.value, prizeTable);
    }

    public int openSwitch(String prizeTable) throws SQLException {
        String sql = "update event_send_prize_properties set status = ? where status = ? and table_name = ?";
        return idao.update(sql, SendProperty.Status.OPEN.value, SendProperty.Status.CLOSE.value, prizeTable);
    }


    public int closeSwitch(String prizeTable, String dbLink) throws SQLException {
        String sql = "update event_send_prize_properties set status = ? where status = ? and table_name = ? and datasource = ?";
        return idao.update(sql, SendProperty.Status.CLOSE.value, SendProperty.Status.OPEN.value, prizeTable, dbLink);
    }

    public int openSwitch(String prizeTable, String dbLink) throws SQLException {
        String sql = "update event_send_prize_properties set status = ? where status = ? and table_name = ? and datasource = ?";
        return idao.update(sql, SendProperty.Status.OPEN.value, SendProperty.Status.CLOSE.value, prizeTable, dbLink);
    }


    public List<SendProperty> getAllSendProperty() throws SQLException {
        String sql = "select * from event_send_prize_properties";
        return idao.queryObjects(SendProperty.class, sql);
    }

    public CommonList<SendProperty> getAllSendProperty(int pageSize, int pageNo, String tableName) throws SQLException {
        Search search = new Search();
        search.addSelectSql("select * from event_send_prize_properties");
        search.addSelectCountSql("select count(1) from event_send_prize_properties");
        search.addOrder("create_time", "desc");
        if (tableName != null && !tableName.isEmpty()) {
            search.addWhere(Search.SEARCH_AND, "table_name like ?", tableName + "%");
        }
        search.setPageNo(pageNo);
        search.setPageSize(pageSize);
        return idao.pagination(search, SendProperty.class);
    }


    public int closeMainSwitch() throws SQLException {
        return updateMainSwitch(SendProperty.Status.OPEN.value, SendProperty.Status.CLOSE.value);
    }

    public int openMainSwitch() throws SQLException {
        return updateMainSwitch(SendProperty.Status.CLOSE.value, SendProperty.Status.OPEN.value);
    }
}
