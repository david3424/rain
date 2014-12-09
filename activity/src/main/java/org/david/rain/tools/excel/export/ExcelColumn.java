package org.david.rain.tools.excel.export;

/**
 * excel列信息
 *
 */
public class ExcelColumn {
    /**
     * 索引
     */
    private int index;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段显示名称
     */
    private String fieldDispName;

    /**
     * 字段类型
     */
    private int type;

    public ExcelColumn() {

    }

    public ExcelColumn(int index, String fieldName, String fieldDispName) {
        super();
        this.index = index;
        this.fieldName = fieldName;
        this.fieldDispName = fieldDispName;
    }

    public ExcelColumn(int index, String fieldName, String fieldDispName, int type) {
        super();
        this.index = index;
        this.fieldName = fieldName;
        this.fieldDispName = fieldDispName;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDispName() {
        return fieldDispName;
    }

    public void setFieldDispName(String fieldDispName) {
        this.fieldDispName = fieldDispName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExcelColumn [fieldDispName=" + fieldDispName + ", fieldName="
                + fieldName + ", index=" + index + ", type=" + type + "]";
    }
}