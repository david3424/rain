package org.david.rain.games.pay.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.david.rain.games.pay.client.exceptions.UtilsException;
import org.springframework.beans.BeanUtils;

/**
 * 扩展org.apache.commons.beanutils.BeanUtils<br>
 */
public class Bean2MapUtils extends BeanUtils {
    /**
     * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
     *
     * @param dest 目标对象，标准的JavaBean
     * @param orig 源对象，可为Map、标准的JavaBean
     */
    public static void map2bean(Object dest, Object orig) throws Exception {
        try {
            if (orig instanceof Map) {
                Iterator names = ((Map) orig).keySet().iterator();
                while (names.hasNext()) {
                    String name = (String) names.next();
                    if (PropertyUtils.isWriteable(dest, name)) {
                        Object value = ((Map) orig).get(name);
                        if (value != null) {
                            PropertyUtils.setSimpleProperty(dest, name, value);
                        }
                    }
                }
            } else {
                Field[] fields = orig.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    String name = fields[i].getName();
                    if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                        Object value = PropertyUtils.getSimpleProperty(orig, name);
                        if (value != null) {
                            PropertyUtils.setSimpleProperty(dest, name, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
        }
    }

    /**
     * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
     *
     * @param orig 源对象，标准的JavaBean
     * @param dest 排除检查的属性，Map
     */
    @SuppressWarnings("rawtypes")
    public static void bean2map(Object orig, Map dest) throws UtilsException {
        Field[] fields = orig.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (!dest.containsKey(name)) {
                if (PropertyUtils.isReadable(orig, name)) {
                    Object value = null;
                    try {
                        value = PropertyUtils.getSimpleProperty(orig, name);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new UtilsException("解析对象报错," + e.getMessage());
                    }
                    if (value != null) {
                        dest.put(name, value);
                    }
                }
            }
        }
    }
}