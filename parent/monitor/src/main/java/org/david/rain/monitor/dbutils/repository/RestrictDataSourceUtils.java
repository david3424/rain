package org.david.rain.monitor.dbutils.repository;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by czw on 13-12-3.
 */
public class RestrictDataSourceUtils extends DataSourceUtils {

    static Logger logger = LoggerFactory.getLogger(RestrictDataSourceUtils.class);

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        try {
            Connection connection = doGetConnection(dataSource);
            if (!TransactionSynchronizationManager.isSynchronizationActive()) {
                DbUtils.close(connection);//IllegalTransactionStateException
                throw new IllegalTransactionStateException("sorry, you can not use a writeDaoImp without" +
                        " a @Transactional annotation tag in you service method, or you method is not public.");
            }
            return connection;
        } catch (SQLException ex) {
            throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
        }
    }
}
