/*
* 时间： 2007-5-18 16:37:42
*/

package org.david.rain.games.pay.client.dao.dbutils.search;

public class Where {
    private String property; //数据库表的字段名；
    private String whereOrAnd;  //字段之间的关系
    private String operation; //操作符
    private String defaultValue;//默认值
    private String type;  //类型
    private String html;  //html元素 ，暂时没用；

    public Where(String property, String whereOrAnd, String operation, String defaultValue,String type,String html) {
        this.property = " " + property + " ";
        this.whereOrAnd = " " + whereOrAnd + " ";
        this.operation = " " + operation + " ";
        this.defaultValue = " " + defaultValue + " ";
        this.html = html;
        this.type = type;
    }

    public Where() {
    }


    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = " " + property + " ";
    }

    public String getWhereOrAnd() {
        return whereOrAnd;
    }

    public void setWhereOrAnd(String whereOrAnd) {
        this.whereOrAnd = " " + whereOrAnd + " ";
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = " " + operation + " ";
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
