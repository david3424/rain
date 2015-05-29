package com.david.web.wanmei.entity;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-4-27
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class ExcelParseExceptInfo extends IdEntity {

    public static final Integer SKIP_HANDLER = 1; //跳过该行
    public static final Integer FORCE_INSERT_HANDLER = 2; //依旧插入。

    private Integer id;
    private Integer row;
    private Integer column;
    private Integer handlerType;
    private String msg;

    public ExcelParseExceptInfo() {
    }

    public ExcelParseExceptInfo(Integer row, Integer column, String msg, Integer handlerType) {
        this.row = row;
        this.column = column;
        this.msg = msg;
        this.handlerType = handlerType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(Integer handlerType) {
        this.handlerType = handlerType;
    }
}
