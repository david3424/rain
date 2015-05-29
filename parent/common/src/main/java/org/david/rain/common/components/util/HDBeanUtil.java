package org.david.rain.common.components.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Date: 12-8-3
 * Time: 上午10:34
 */
public class HDBeanUtil {
    public static Field getFieldBean(Object obj) {
        Field fieldbean = new Field();
//            System.out.println("-------------------fields.length:"+fields.length);
        ArrayList fieldnames = new ArrayList();
        ArrayList params = new ArrayList();
        ArrayList fieldvalues = new ArrayList();
        try {
            Map map = PropertyUtils.describe(obj);
            fieldnames.add("id");
            fieldbean.setKeyFieldValue(map.get("id"));
            map.remove("id");
            map.remove("class");
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Entry entry = (Entry) iterator.next();
                fieldnames.add(entry.getKey());
                params.add(entry.getKey());
                fieldvalues.add(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fieldbean.setFieldNames(fieldnames);
        fieldbean.setParams(params);
        fieldbean.setParamValues(fieldvalues);
        return fieldbean;
    }
}
