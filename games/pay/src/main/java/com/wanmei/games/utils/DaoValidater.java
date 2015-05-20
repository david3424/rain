package com.wanmei.games.utils;


import com.wanmei.games.utils.entity.HdTable;

/**
 * 数据库操作对象时校验类.
 * Date: 12-11-20
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
public class DaoValidater {
    public static void assertHasHdTable(Class clazz) {
//        if (null == clazz.getAnnotation(HdTable.class)) {
        if (!clazz.isAnnotationPresent(HdTable.class)) {
            throw new RuntimeException("类" + clazz.getName() + "缺少com.david.hdutil.table.annotations.HdTable注解");
        }
    }
}
