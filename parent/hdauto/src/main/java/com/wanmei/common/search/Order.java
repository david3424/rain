/*
* 时间： 2007-5-18 16:37:42
*  北京完美时空网络技术有限公司
*/

package com.david.web.wanmei.common.search;

/**
 * Created by IntelliJ IDEA.
 * User: 李亮阳
 * Date: 2006-11-3
 * Time: 11:06:27
 * 定义排序对象；
 */
public class Order {
// 要排序得属性；字段
    private String property;
// 排序方式；
    private String descOrAsc;

    public Order(){}

    public Order(String property, String descOrAsc) {
        this.property = property;
        this.descOrAsc = descOrAsc;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDescOrAsc() {
        return descOrAsc;
    }

    public void setDescOrAsc(String descOrAsc) {
        this.descOrAsc = descOrAsc;
    }
}
