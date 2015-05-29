package com.david.web.wanmei.repository;


import com.david.web.wanmei.entity.HdTable;

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
            StringBuilder sb = new StringBuilder("类").append(clazz.getName()).append("缺少com.wanmei.hdutil.table.annotations.HdTable注解");
            throw new RuntimeException(sb.toString());
        }
    }
}
