package org.david.rain.common.repository;


import org.apache.commons.beanutils.PropertyUtils;
import org.david.rain.common.entity.HdTable;
import org.david.rain.common.entity.ThisIgnore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SqlResolver {
    private Object object;
    private String table;
    private List<String> paramNames;
    private List<String> ignoreParamNames;
    private List<Object> paramValues;

    public SqlResolver(Object object) {
        this.object = object;

        DaoValidater.assertHasHdTable(object.getClass());
        table = object.getClass().getAnnotation(HdTable.class).value();

//        ThisIgnore.class = object.getClass().getAnnotation(ThisIgnore.class).value();
        ignoreParamNames = new ArrayList<>();
        Class c = object.getClass();
        Field[] fArr = c.getDeclaredFields();
        for (Field f : fArr) {
            if (f.isAnnotationPresent(ThisIgnore.class)) {
                ignoreParamNames.add(f.getName());
            }
        }

        initFieldAndValue();
    }

    /**
     *
     */
    private void initFieldAndValue() {
        Map map = null;
        try {
            map = PropertyUtils.describe(object);
            map.remove("class");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert map != null;
        Iterator iterator = map.entrySet().iterator();
        paramNames = new ArrayList<>();
        paramValues = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (ignoreParamNames.contains(entry.getKey())) {
                continue;
            }
            paramNames.add((String) entry.getKey());
            paramValues.add(entry.getValue());
        }

    }

    public String insertSql() {
        StringBuffer sql = new StringBuffer("INSERT INTO " + table + "(");
        StringBuffer prepared = new StringBuffer();
        for (int i = 0; i < paramNames.size(); i++) {
            if ("id".equals(paramNames.get(i))) {
                paramValues.remove(i);
                continue;
            }
            if (prepared.length() == 0) {
                sql.append(paramNames.get(i).toLowerCase());
                prepared.append("?");
            } else {
                sql.append(",").append(paramNames.get(i).toLowerCase());
                prepared.append(",?");
            }
        }
        sql.append(") VALUES (").append(prepared).append(")");
        return sql.toString();
    }

    public String updateSql() {
        StringBuffer sql = new StringBuffer("update " + table + " set ");
        Object id = null;
        for (int i = 0; i < paramNames.size(); i++) {
            if ("id".equals(paramNames.get(i)) || "null".equals(paramNames.get(i))) {
                id = paramValues.get(i);
                paramValues.remove(i);
                continue;
            }
            if (i == 0) {
                sql.append(paramNames.get(i).toLowerCase()).append("=?");
            } else {
                sql.append(",").append(paramNames.get(i).toLowerCase()).append("=?");
            }
        }
        sql.append(" where id=").append(id);
        return sql.toString();
    }

    public Object[] paramValues() {
        return paramValues.toArray();
    }
}
