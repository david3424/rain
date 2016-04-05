package org.david.rain.monitor.dbutils.repository;


import org.david.rain.monitor.dbutils.repository.anotation.HdTable;

/**
 * 数据库操作对象时校验类.
 * User: gameuser
 * Date: 12-11-20
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
public class DaoValidater {
    public static void assertHasHdTable(Class clazz) {
//        if (null == clazz.getAnnotation(HdTable.class)) {
        if (!clazz.isAnnotationPresent(HdTable.class)) {
            throw new RuntimeException("");
        }
    }
}
