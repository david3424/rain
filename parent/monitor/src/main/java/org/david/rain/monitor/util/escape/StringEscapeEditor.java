package org.david.rain.monitor.util.escape;

import org.springframework.web.util.HtmlUtils;

import java.beans.PropertyEditorSupport;

/**
 * 
 * 
 * 功能描述： 自定义属性编辑器StringEscapeEditor
 * 
 * @version 1.0.0
 */
public class StringEscapeEditor extends PropertyEditorSupport {
    /**
     * escapeHTML
     */
    private boolean escapeHTML;

    /**
     * StringEscapeEditor constructor
     */
    public StringEscapeEditor() {
        super();
    }

    /**
     * StringEscapeEditor constructor
     * 
     * @param escapeHTML flag
     */
    public StringEscapeEditor(boolean escapeHTML) {
        super();
        this.escapeHTML = escapeHTML;
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                // 将String类型的value转化为html的格式
                value = HtmlUtils.htmlEscape(value);
                setValue(value);
            }

        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

}