package org.david.rain.beta.dao;

import org.david.rain.beta.util.Condition;
import org.david.rain.beta.util.ConditionDef;
import org.david.rain.beta.util.SQLUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 2014/10/30.
 */
@Repository
public class BaseDao {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 保存新增的实体对象
     * @param bean
     * @return
     */
    public boolean baseSave(Object bean){
        String sql = SQLUtils.buildInsertSql(bean.getClass());
        SqlParameterSource sps = new BeanPropertySqlParameterSource(bean);
        return this.namedParameterJdbcTemplate.update(sql, sps) > 0;
    }

    /**
     * 根据主键保存修改的实体对象
     * @param bean
     * @return
     */
    public boolean baseSaveUpdate(Object bean){
        String sql = SQLUtils.buildUpdateSql(bean.getClass());
        SqlParameterSource sps = new BeanPropertySqlParameterSource(bean);
        return this.namedParameterJdbcTemplate.update(sql, sps) > 0;
    }

    /**
     * 根据bean的部分字段的条件来更新bean的信息
     * @param bean
     * @param fileds
     * @return
     * @throws Exception
     */
    public boolean baseSaveUpdateWithColumn(Object bean,String[] fileds) throws Exception{
        String sql = SQLUtils.buildUpdateSqlByColumns(bean.getClass(), fileds);
        SqlParameterSource sps = new BeanPropertySqlParameterSource(bean);
        return this.namedParameterJdbcTemplate.update(sql, sps)>0? true:false;
    }

    /**
     * 根据bean的pk来删除bean
     * @param bean
     * @return
     */
    public boolean baseDelete(Object bean){
        String sql = SQLUtils.buildDeleteSql(bean.getClass());
        SqlParameterSource sps = new BeanPropertySqlParameterSource(bean);
        return this.namedParameterJdbcTemplate.update(sql, sps)>0? true:false;
    }

    /**
     * 根据bean的部分字段的条件来删除bean
     * @param bean
     * @param fileds
     * @return
     * @throws Exception
     */
    public boolean baseDeleteWithColumn(Object bean,String[] fileds) throws Exception{
        String sql = SQLUtils.buildDeleteSqlByColumns(bean.getClass(), fileds);
        SqlParameterSource sps = new BeanPropertySqlParameterSource(bean);
        return this.namedParameterJdbcTemplate.update(sql, sps)>0? true:false;
    }

   /* *//**
     * 自动分页/不分页查询返回list
     * @param cs
     * @param conditionDef
     * @param paramMap
     * @return
     *//*
    public List baseQueryForList(Class cs, ConditionDef conditionDef, Map paramMap){
        Condition condition=new Condition(conditionDef,paramMap);
        String sql = SQLUtils.buildSelectSql(cs) + condition.getConditionClauseWithWhere();

        if(PagingUtils.isPagingSearchRequest(paramMap)) {
            return PagingUtils.pagingQuery(namedParameterJdbcTemplate, sql, paramMap);
        }else {
            return namedParameterJdbcTemplate.queryForList(sql, paramMap);
        }
    }*/

    /**
     * 查询满足条件的单条记录的实体对象，如果超过1条则抛出异常，没查询到则返回null
     * @param cs
     * @param conditionDef
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Object baseQueryForEntity(Class cs, ConditionDef conditionDef, Map paramMap) throws Exception{
        Condition condition=new Condition(conditionDef,paramMap);
        String sql = SQLUtils.buildSelectSql(cs) + condition.getConditionClauseWithWhere();
        List list = this.namedParameterJdbcTemplate.queryForList(sql, paramMap);

        if(null==list || list.size()==0 || list.size()>1){
            return null;
        }else if(list.size()>1){
            throw new Exception("query return record more then one!!");
        }else{
            Map map = (Map)list.get(0);
            return SQLUtils.coverMapToBean(map, cs);
        }
    }


    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}
