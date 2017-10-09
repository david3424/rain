package org.david.rain.cloud.start.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface CommonDao {
	/**
	 * 
	 * @Title: queryList
	 * @Description: TODO 返回指定sql语句结果
	 * @param sql
	 *            查询语句
	 * @return
	 * @throws
	 */
	public List queryList(String sql);

	/**
	 * 
	 * @Title: queryList
	 * @Description: TODO 返回指定sql语句结果
	 * @param sql
	 * @param o
	 *            参数集
	 * @return
	 * @throws
	 */
	public List queryList(String sql, Object[] o);

	public <T> List<T> queryObjList(String sql, Object[] args, Class<T> clazz);
	
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType);

	/**
	 * 
	 * @Title: queryForInt
	 * @Description: TODO 返回条数信息
	 * @param sql
	 * @param o
	 * @return
	 * @throws
	 */
	public int queryForInt(String sql, Object[] o);

	/**
	 * 
	 * @Title: addOrUpdate
	 * @Description: TODO 添加、修改、删除
	 * @param sql
	 * @param o
	 * @return
	 * @throws
	 */
	public int addOrUpdate(String sql, Object[] o);

	public int addOrUpdateForTransaction(String sql, String sql1, String sql2,
                                         List<List<Object>> userCondition, String[]... o) throws Exception;

	public int addOrUpdateForTransactionOneTable(String sql, String sqlselect,
                                                 String[] o) throws Exception;

	public <T> T queryObj(String sql, Object[] args, Class<T> clazz);
	
	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper);
	
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss);
	
	public void execSql(String sqlString);

}
