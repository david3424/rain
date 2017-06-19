package org.david.pattern.template;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xdw9486 on 2017/2/13.
 * Callback接口
 */
public interface StatementCallback {


    Object doInStatement(ResultSet resultSet) throws SQLException;

}
