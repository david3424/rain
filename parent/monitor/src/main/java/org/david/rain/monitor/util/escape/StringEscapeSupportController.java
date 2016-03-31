package org.david.rain.monitor.util.escape;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * 
 * 功能描述：注册自定义的StringEscapeEditor属性编辑器
 * 
 * @version 1.0.0
 */
public class StringEscapeSupportController {
    /** escapeHTML */
    private boolean escapeHTML = true;

    /**
     * 
     * 功能描述: <br>
     * 将自定义的StringEscapeEditor属性编辑器注册到绑定器对象中
     * 
     * @param binder 参考说明
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor(escapeHTML));
    }

    /**
     * 
     * 功能描述: <br>
     * setEscapeHTML
     * 
     * @param escapeHTML 参考说明
     */
    protected void setEscapeHTML(boolean escapeHTML) {
        this.escapeHTML = escapeHTML;
    }

}
